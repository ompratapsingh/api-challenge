package com.disney.studios.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PetImage {

	private int id;
	private int breedId;
	private String url;
	private int likeCount;

	@JsonBackReference
	private PetBreedDetails breedDetails;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@JsonIgnore
	public int getBreedId() {
		return breedId;
	}

	public void setBreedId(int breedId) {
		this.breedId = breedId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public PetBreedDetails getBreedDetails() {
		return breedDetails;
	}

	public void setBreedDetails(PetBreedDetails breedDetails) {
		this.breedDetails = breedDetails;
	}

}
