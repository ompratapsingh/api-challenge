package com.disney.studios.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.disney.studios.domain.PetImage;
import com.disney.studios.util.PetResultSetExtractor;

@Repository
public class PetRepository implements IPetRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(PetRepository.class);
	
	private static final String GET_ALL_PET_DETAILS_QUERY = "SELECT breed.ID,breed_name, origin, colors, life_expectancy, image.id as image_id, image.image_url,count(dislike.image_id) as like_count FROM pet_breed_details as breed INNER JOIN PET_IMAGE_DETAILS as image "
			+ "ON  breed.id=image.breed_id LEFT JOIN PET_IMAGE_LIKE_DISLIKE as dislike ON image.id=dislike.image_id  GROUP BY image.id";

	private static final String GET_PET_DETAILS_BY_BREED_NAME_QUERY = "SELECT breed.ID,breed_name, origin, colors, life_expectancy, image.id as image_id, image.image_url,count(dislike.image_id) as like_count FROM pet_breed_details as breed INNER JOIN PET_IMAGE_DETAILS as image "
			+ "ON  breed.id=image.breed_id LEFT JOIN PET_IMAGE_LIKE_DISLIKE as dislike ON image.id=dislike.image_id where breed.breed_name like ?  GROUP BY image.id ";

	private static final String IS_USER_LIKE_EXIST_QUERY = "SELECT COUNT(*) FROM PET_IMAGE_LIKE_DISLIKE WHERE IMAGE_ID= ?  AND LIKE_BY = ?";

	private static final String ADD_IMAGE_LIKE_QUERY = "INSERT INTO PET_IMAGE_LIKE_DISLIKE (LIKE_BY,IMAGE_ID) VALUES (?,?)";

	private static final String REMOVE_IMAGE_LIKE_QUERY = "DELETE FROM PET_IMAGE_LIKE_DISLIKE WHERE IMAGE_ID= ?  AND LIKE_BY = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<PetImage> getAllPetDetails() {
		return jdbcTemplate.query(GET_ALL_PET_DETAILS_QUERY, new PetResultSetExtractor());

	}

	public List<PetImage> fetchBreedDetailsByName(String breedName) {
		return jdbcTemplate.query(GET_PET_DETAILS_BY_BREED_NAME_QUERY, new String[] { breedName },
				new PetResultSetExtractor());
	}

	public boolean isUserLikeExist(String emailId, int imageId) {
		int count = jdbcTemplate.queryForObject(IS_USER_LIKE_EXIST_QUERY, new Object[] { imageId, emailId },
				Integer.class);
		return count > 0;
	}

	public boolean addLike(String emailId, int imageId) {
		int status = jdbcTemplate.update(ADD_IMAGE_LIKE_QUERY, emailId, imageId);
		LOGGER.debug("Image like added status: {}", status);
		return status > 0;
	}

	public boolean removeLike(String emailId, int imageId) {
		return jdbcTemplate.update(REMOVE_IMAGE_LIKE_QUERY, imageId, emailId) == 1;
	}
}
