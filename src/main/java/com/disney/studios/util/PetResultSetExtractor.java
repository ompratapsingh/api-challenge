package com.disney.studios.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.disney.studios.domain.PetBreedDetails;
import com.disney.studios.domain.PetImage;

public class PetResultSetExtractor implements ResultSetExtractor<List<PetImage>> {

	private List<PetImage> petImages = new ArrayList<>();

	@Override
	public List<PetImage> extractData(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			PetImage petImage = new PetImage();
			petImage.setBreedId(resultSet.getInt("ID"));
			petImage.setId(resultSet.getInt("image_id"));
			petImage.setUrl(resultSet.getString("IMAGE_URL"));
			petImage.setLikeCount(resultSet.getInt("like_count"));
			PetBreedDetails breedDetails = new PetBreedDetails();
			breedDetails.setId(resultSet.getInt("ID"));
			breedDetails.setBreedName(resultSet.getString("BREED_NAME"));
			breedDetails.setColors(resultSet.getString("COLORS"));
			breedDetails.setOrigin(resultSet.getString("ORIGIN"));
			breedDetails.setLifeExpectancy(resultSet.getString("life_expectancy"));
			petImage.setBreedDetails(breedDetails);
			petImages.add(petImage);

		}
		return petImages;
	}

}
