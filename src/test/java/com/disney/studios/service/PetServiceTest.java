package com.disney.studios.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.CollectionUtils;

import com.disney.studios.domain.PetBreedDetails;
import com.disney.studios.domain.PetImage;
import com.disney.studios.exception.NoResultFoundException;
import com.disney.studios.repository.PetRepository;

@RunWith(MockitoJUnitRunner.class)
public class PetServiceTest {

	@InjectMocks
	private PetService petService;

	@Mock
	private PetRepository petRepository;

	private static final int IMAGE_ID_1 = 1;
	private static final int IMAGE_ID_2 = 2;

	@Test
	public void testGetAllPetImagesDetails() {
		when(petRepository.getAllPetDetails()).thenReturn(getPetImage());
		List<PetBreedDetails> petBreedDetails = petService.getAllPetImagesDetails();
		assertNotNull(petBreedDetails);
		assertTrue(!CollectionUtils.isEmpty(petBreedDetails));
	}

	@Test(expected = NoResultFoundException.class)
	public void testGetAllPetImagesDetailsWithEmptyResultSet() {
		when(petRepository.getAllPetDetails()).thenReturn(null);
		petService.getAllPetImagesDetails();
	}

	@Test
	public void testGetAllPetImagesDetailsWithShortedImage() {
		when(petRepository.getAllPetDetails()).thenReturn(getPetImage());
		List<PetBreedDetails> petBreedDetails = petService.getAllPetImagesDetails();
		assertNotNull(petBreedDetails);
		assertTrue(!CollectionUtils.isEmpty(petBreedDetails));
		assertEquals(IMAGE_ID_2, petBreedDetails.get(0).getImages().get(0).getId());
	}

	private List<PetImage> getPetImage() {
		PetImage imageOne = new PetImage();
		PetBreedDetails breedDetailsOne = new PetBreedDetails();
		breedDetailsOne.setBreedName("Test1");
		breedDetailsOne.setColors("Black");
		breedDetailsOne.setLifeExpectancy("10 - 14  years");
		imageOne.setBreedDetails(breedDetailsOne);
		imageOne.setBreedId(1);
		imageOne.setId(IMAGE_ID_1);
		imageOne.setUrl("http://localhost:8080/23ctsx.jpg");
		imageOne.setLikeCount(1);

		PetImage imageTwo = new PetImage();
		PetBreedDetails breedDetailsTwo = new PetBreedDetails();
		breedDetailsTwo.setBreedName("Test2");
		breedDetailsTwo.setColors("Black");
		breedDetailsTwo.setLifeExpectancy("10 - 14  years");
		imageTwo.setBreedDetails(breedDetailsTwo);
		imageTwo.setBreedId(1);
		imageTwo.setId(IMAGE_ID_2);
		imageTwo.setUrl("http://localhost:8080/1254tsx.jpg");
		imageTwo.setLikeCount(2);
		return Arrays.asList(imageOne, imageTwo);
	}
	
	// TODO: Need to add more test case
}
