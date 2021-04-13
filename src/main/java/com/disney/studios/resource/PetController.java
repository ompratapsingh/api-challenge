package com.disney.studios.resource;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.disney.studios.domain.PetBreedDetails;
import com.disney.studios.service.IPetService;
import com.disney.studios.util.CommonUtil;

@RestController
@RequestMapping("v1/pet")
public class PetController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PetController.class);
	
	@Autowired
	private IPetService petservice;

	@GetMapping(value = "/fetch-all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<PetBreedDetails> getAllAvailablePet() {
		try {
			return petservice.getAllPetImagesDetails();
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage(), exception);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Process Failed.");
		}

	}

	@RequestMapping(value = "breed", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public PetBreedDetails getBreedDetails(@RequestParam("name") String breedName) {
		if (StringUtils.isNotBlank(breedName)) {
			return petservice.getBreedDetailsByName(breedName);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Attribute.");
		}
	}

	@PatchMapping(value = "/image/{imageId}/like/{value}")
	public ResponseEntity<String> imageLikeDisLike(@PathVariable(value = "imageId") String imageId,
			@PathVariable(value = "value") String value, @RequestHeader(value = "email-id") String emailId) {
		if (CommonUtil.isValidRequest(imageId, emailId, value)) {
			boolean status = petservice.addImageLikeDisLike(emailId, imageId, value);
			if (status) {
				return new ResponseEntity<>("Accepted", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("", HttpStatus.CONTINUE);
			}

		}
		return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
	}

}
