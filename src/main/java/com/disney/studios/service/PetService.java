package com.disney.studios.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.disney.studios.domain.LikeDisLikeEnum;
import com.disney.studios.domain.PetBreedDetails;
import com.disney.studios.domain.PetImage;
import com.disney.studios.exception.NoResultFoundException;
import com.disney.studios.exception.UnSupportedOperation;
import com.disney.studios.repository.IPetRepository;
import com.disney.studios.util.PetImageGroupUtil;

@Service
public class PetService implements IPetService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PetService.class);

	@Autowired
	private IPetRepository petRepository;

	public List<PetBreedDetails> getAllPetImagesDetails() {
		List<PetImage> petImages = petRepository.getAllPetDetails();
		if (!CollectionUtils.isEmpty(petImages)) {
			LOGGER.debug("Total image count available in application= {}", petImages.size());
			Map<Integer, Pair<PetBreedDetails, List<PetImage>>> imageGroupByBreed = PetImageGroupUtil
					.getPetImageGroupByBreed(petImages);
			LOGGER.debug("Image Group By Breed details : {}", imageGroupByBreed);
			imageGroupByBreed = sortByImageLikeCount(imageGroupByBreed);
			LOGGER.debug("Image Group By Breed details after sorted by like count: {}", imageGroupByBreed);
			return buildResponse(imageGroupByBreed);
		} else {
			throw new NoResultFoundException("No data found.");
		}
	}

	private Map<Integer, Pair<PetBreedDetails, List<PetImage>>> sortByImageLikeCount(
			Map<Integer, Pair<PetBreedDetails, List<PetImage>>> imageGroupByBreed) {
		Map<Integer, Pair<PetBreedDetails, List<PetImage>>> breedImageMapper = new HashMap<>();
		for (Map.Entry<Integer, Pair<PetBreedDetails, List<PetImage>>> imageEntry : imageGroupByBreed.entrySet()) {
			List<PetImage> sortedImage = sortImageByLikeCount(imageEntry.getValue().getRight());
			breedImageMapper.put(imageEntry.getKey(), Pair.of(imageEntry.getValue().getLeft(), sortedImage));
		}
		return breedImageMapper;
	}

	private List<PetBreedDetails> buildResponse(Map<Integer, Pair<PetBreedDetails, List<PetImage>>> imageGroupByBreed) {
		List<PetBreedDetails> breedDetails = new ArrayList<>();
		for (Map.Entry<Integer, Pair<PetBreedDetails, List<PetImage>>> imageEntry : imageGroupByBreed.entrySet()) {
			Pair<PetBreedDetails, List<PetImage>> pair = imageEntry.getValue();
			PetBreedDetails petBreedDetails = pair.getLeft();
			petBreedDetails.setImages(pair.getRight());
			breedDetails.add(petBreedDetails);
		}
		return breedDetails;
	}

	public PetBreedDetails getBreedDetailsByName(String breedName) {
		List<PetImage> petImages = petRepository.fetchBreedDetailsByName(breedName);
		if (!org.springframework.util.CollectionUtils.isEmpty(petImages)) {
			petImages = sortImageByLikeCount(petImages);
			List<PetImage> images = new ArrayList<>();
			PetBreedDetails breedDetails = petImages.get(0).getBreedDetails();
			for (PetImage image : petImages) {
				images.add(image);
			}
			breedDetails.setImages(images);
			return breedDetails;
		} else {
			throw new NoResultFoundException("No data found.");
		}

	}

	private List<PetImage> sortImageByLikeCount(List<PetImage> petImages) {
		return petImages.stream()
				.sorted((imageOne, imageTwo) -> Integer.compare(imageTwo.getLikeCount(), imageOne.getLikeCount()))
				.collect(Collectors.toList());

	}

	/**
	 * Method to perform Like/Dislike for image.
	 * 
	 * @param emailId
	 *            Actor email-id
	 * @param imageId
	 *            Pet imageId
	 * @param value
	 *            Either Liked or DisLiked
	 * @return Either true or false
	 */
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public boolean addImageLikeDisLike(String emailId, String imageId, String value) {
		int intImageId = Integer.parseInt(imageId);
		boolean status = petRepository.isUserLikeExist(emailId, intImageId);
		if (status && LikeDisLikeEnum.LIKED.name().equalsIgnoreCase(value)) {
			LOGGER.error("User already liked imageId= {}", imageId);
			throw new UnSupportedOperation("User already liked image.");
		} else if (!status && LikeDisLikeEnum.DISLIKED.name().equalsIgnoreCase(value)) {
			LOGGER.error("User like not exist on imageId= {}", imageId);
			throw new UnSupportedOperation("User like not exist.");
		}
		boolean isCompleted = false;
		if (LikeDisLikeEnum.LIKED.name().equalsIgnoreCase(value)) {
			isCompleted = petRepository.addLike(emailId, intImageId);
			LOGGER.debug("Like Status: {}", isCompleted);
		} else if (LikeDisLikeEnum.DISLIKED.name().equalsIgnoreCase(value)) {
			isCompleted = petRepository.removeLike(emailId, intImageId);
			LOGGER.debug("Dislike Status: {}", isCompleted);
		}

		return isCompleted;
	}

}
