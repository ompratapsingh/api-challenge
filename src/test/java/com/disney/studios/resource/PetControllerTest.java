package com.disney.studios.resource;

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

import com.disney.studios.domain.PetBreedDetails;
import com.disney.studios.service.PetService;

@RunWith(MockitoJUnitRunner.class)
public class PetControllerTest {

	@InjectMocks
	private PetController petController;

	@Mock
	private PetService petservice;

	@Test
	public void testFetchAllPetDetails() {
		when(petservice.getAllPetImagesDetails()).thenReturn(getTestCase());
		List<PetBreedDetails> response = petController.getAllAvailablePet();
		assertNotNull(response);
		assertTrue(response.size() > 0);
	}

	private List<PetBreedDetails> getTestCase() {
		PetBreedDetails breedDetails = new PetBreedDetails();
		breedDetails.setBreedName("Test");
		breedDetails.setColors("Black");
		breedDetails.setLifeExpectancy("10 - 14 years");
		return Arrays.asList(breedDetails);
	}
}
