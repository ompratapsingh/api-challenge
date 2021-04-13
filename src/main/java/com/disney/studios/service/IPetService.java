package com.disney.studios.service;

import java.util.List;

import com.disney.studios.domain.PetBreedDetails;

public interface IPetService {

	List<PetBreedDetails> getAllPetImagesDetails();

	PetBreedDetails getBreedDetailsByName(String breedName);

	boolean addImageLikeDisLike(String emailId, String imageId, String value);
}
