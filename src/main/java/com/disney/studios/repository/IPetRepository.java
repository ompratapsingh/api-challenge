package com.disney.studios.repository;

import java.util.List;

import com.disney.studios.domain.PetImage;

public interface IPetRepository {
	
	List<PetImage> getAllPetDetails();

	List<PetImage> fetchBreedDetailsByName(String breedName);

	boolean addLike(String emailId, int imageId);

	boolean removeLike(String emailId, int imageId);
	
	boolean isUserLikeExist(String emailId, int imageId);
}
