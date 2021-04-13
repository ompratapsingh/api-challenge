package com.disney.studios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.disney.studios.domain.PetBreedDetails;
import com.disney.studios.domain.PetImage;

@Component
public class PetLoader implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(PetLoader.class);

	private static final String GET_PET_BREED_DETAILS_QUERY = "SELECT id, breed_name, origin, colors, life_expectancy FROM pet_breed_details";

	private static final String SAVE_PET_IMAGE_DETAILS_QUERY = "INSERT INTO PET_IMAGE_DETAILS(BREED_ID,IMAGE_URL) VALUES (?,?)";

	// Resources to the different files we need to load.
	@Value("classpath:breed_data/labrador.txt")
	private Resource labradors;

	@Value("classpath:breed_data/pug.txt")
	private Resource pugs;

	@Value("classpath:breed_data/retriever.txt")
	private Resource retrievers;

	@Value("classpath:breed_data/yorkie.txt")
	private Resource yorkies;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Load the different breeds into the data source after the application is
	 * ready.
	 *
	 * @throws Exception
	 *             In case something goes wrong while we load the breeds.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		List<PetBreedDetails> breedDetails = getPetBreedDetails();
		LOGGER.debug("Total available pet breed in application is <{}>", breedDetails.size());
		savePetImageByBreedDetails(breedDetails);
	}

	/**
	 * Reads the list of dogs in a category and (eventually) add them to the data
	 * source.
	 * 
	 * @param List<breedDetails>
	 *            The breed details as Master data.
	 */
	private void savePetImageByBreedDetails(List<PetBreedDetails> breedDetails) {
		for (PetBreedDetails breed : breedDetails) {
			LOGGER.debug("Saving breed: {}", breed.getBreedName());
			Resource resource = null;
			if (breed.getBreedName().equalsIgnoreCase("Labrador")) {
				resource = this.labradors;
			} else if (breed.getBreedName().equalsIgnoreCase("Retriver")) {
				resource = this.retrievers;
			} else if (breed.getBreedName().equalsIgnoreCase("Pug")) {
				resource = this.pugs;
			} else if (breed.getBreedName().equalsIgnoreCase("Yorkie")) {
				resource = this.yorkies;
			}
			List<PetImage> petImages = loadImages(breed.getId(), resource);
			LOGGER.debug("Image count= {} for {} breed.", petImages.size(), breed.getBreedName());
			saveImage(petImages);
		}
	}

	private void saveImage(List<PetImage> petImages) {
		jdbcTemplate.batchUpdate(SAVE_PET_IMAGE_DETAILS_QUERY, petImages, petImages.size(),
				new ParameterizedPreparedStatementSetter<PetImage>() {
					@Override
					public void setValues(PreparedStatement ps, PetImage argument) throws SQLException {
						ps.setInt(1, argument.getBreedId());
						ps.setString(2, argument.getUrl());
					}

				});
	}

	private List<PetImage> loadImages(int breedId, Resource resource) {
		List<PetImage> petImages = new ArrayList<>();
		if (resource != null) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
				String line;
				while ((line = br.readLine()) != null) {
					LOGGER.debug(line);
					PetImage petImage = new PetImage();
					petImage.setBreedId(breedId);
					petImage.setUrl(line);
					petImages.add(petImage);
				}
			} catch (IOException e) {
				LOGGER.error("Failed to load image details for {} ", resource.getFilename(), e);
			}
		}
		return petImages;
	}

	private List<PetBreedDetails> getPetBreedDetails() {
		List<PetBreedDetails> breedDetails = new ArrayList<>();
		jdbcTemplate.query(GET_PET_BREED_DETAILS_QUERY, new ResultSetExtractor<List<PetBreedDetails>>() {
			@Override
			public List<PetBreedDetails> extractData(ResultSet resultSet) throws SQLException {
				while (resultSet.next()) {
					PetBreedDetails petBreedDetails = new PetBreedDetails();
					petBreedDetails.setId(resultSet.getInt("id"));
					petBreedDetails.setBreedName(resultSet.getString("breed_name"));
					petBreedDetails.setColors(resultSet.getString("colors"));
					petBreedDetails.setLifeExpectancy(resultSet.getString("life_expectancy"));
					petBreedDetails.setOrigin(resultSet.getString("origin"));
					breedDetails.add(petBreedDetails);
				}
				return breedDetails;
			}
		});
		return breedDetails;
	}
}
