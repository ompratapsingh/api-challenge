package com.disney.studios.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetBreedDetails {

	private int id;
	private String breedName;
	private String origin;
	private String colors;
	private String lifeExpectancy;
	
	
	@JsonManagedReference
	private List<PetImage> images;

	public int getId() {

		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBreedName() {
		return breedName;
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getColors() {
		return colors;
	}

	public void setColors(String colors) {
		this.colors = colors;
	}

	public String getLifeExpectancy() {
		return lifeExpectancy;
	}

	public void setLifeExpectancy(String lifeExpectancy) {
		this.lifeExpectancy = lifeExpectancy;
	}

	public List<PetImage> getImages() {
		return images;
	}

	public void setImages(List<PetImage> images) {
		this.images = images;
	}

}
