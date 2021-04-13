package com.disney.studios.util;

import org.apache.commons.lang3.StringUtils;

import com.disney.studios.domain.LikeDisLikeEnum;

public class CommonUtil {

	private CommonUtil() {
		// No Instance
	}

	public static boolean isNumeric(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static boolean isValidLikeDisLikeValue(String value) {
		return LikeDisLikeEnum.LIKED.name().equalsIgnoreCase(value)
				|| LikeDisLikeEnum.DISLIKED.name().equalsIgnoreCase(value);
	}

	public static boolean isValidRequest(String imageId, String emailId, String value) {
		return isNumeric(imageId) && StringUtils.isNotBlank(emailId) && StringUtils.isNotBlank(value);
	}
}
