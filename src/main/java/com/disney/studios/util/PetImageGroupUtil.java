package com.disney.studios.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.disney.studios.domain.PetBreedDetails;
import com.disney.studios.domain.PetImage;

public class PetImageGroupUtil {

	private PetImageGroupUtil() {
		// No Instance
	}

	public static Map<Integer, Pair<PetBreedDetails, List<PetImage>>> getPetImageGroupByBreed(
			List<PetImage> petImages) {
		Map<Integer, Pair<PetBreedDetails, List<PetImage>>> breedImageMapper = new HashMap<>();
		for (PetImage petImage : petImages) {
			int breedId = petImage.getBreedId();
			if (!breedImageMapper.containsKey(breedId)) {
				List<PetImage> images = new ArrayList<>();
				images.add(petImage);
				breedImageMapper.put(breedId, Pair.of(petImage.getBreedDetails(), images));
			} else {
				breedImageMapper.get(breedId).getRight().add(petImage);
			}
		}
		return breedImageMapper;
	}
}
