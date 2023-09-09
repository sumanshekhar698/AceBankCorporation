package com.acebank.utils;

import java.util.concurrent.ThreadLocalRandom;

public class GeneralUtil {
	/**
	 * Uses aadharNumber and firstName to generate a Customer ID
	 * 
	 * @param aadharNumber
	 * @param firstName
	 * @return alpha numeric 6 digit string
	 */

	public static String getRandomAlphaNumbericID(long aadharNumber, String firstName) {
		var aadharString = String.valueOf(aadharNumber);
		var stringBuilder = new StringBuilder();
		for (int i = 0; i < 6; i++) {

			if (i < 3) {
				stringBuilder.append(aadharString.charAt(ThreadLocalRandom.current().nextInt(aadharString.length())));
			}
			if (i >= 3) {
				stringBuilder.append(firstName.charAt(ThreadLocalRandom.current().nextInt(firstName.length())));
			}
		}

		return stringBuilder.toString();

	}
}
