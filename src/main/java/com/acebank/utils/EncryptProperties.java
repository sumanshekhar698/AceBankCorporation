package com.acebank.utils;

import java.io.FileInputStream;

import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncryptProperties {

	/*
	 * The code snippet first creates a Cipher object and initializes it with the
	 * encryption algorithm AES in ECB mode with PKCS5 padding. This is done by the
	 * following lines of code:
	 * 
	 * Sure, I can explain the code snippet you provided in more detail.
	 * 
	 * The code snippet first creates a Cipher object and initializes it with the
	 * encryption algorithm AES in ECB mode with PKCS5 padding. This is done by the
	 * following lines of code:
	 * 
	 * Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	 * cipher.init(Cipher.ENCRYPT_MODE, new
	 * SecretKeySpec("my-secret-key".getBytes(), "AES"));
	 * 
	 * The Cipher class is a Java class that provides encryption and decryption
	 * services. The getInstance() method is used to create a Cipher object. The
	 * first parameter to the getInstance() method is the transformation string. The
	 * transformation string specifies the encryption algorithm, mode, and padding
	 * scheme. In this case, the transformation string is AES/ECB/PKCS5Padding.
	 * 
	 * The AES algorithm is a block cipher that encrypts data in blocks of 128 bits.
	 * The ECB mode is a simple encryption mode that encrypts each block of data
	 * independently. The PKCS5Padding padding scheme is used to pad the data to a
	 * multiple of the block size.
	 * 
	 * The init() method is used to initialize the Cipher object. The first
	 * parameter to the init() method is the encryption mode, which in this case is
	 * Cipher.ENCRYPT_MODE. The second parameter to the init() method is the secret
	 * key. The secret key is used to encrypt and decrypt the data.
	 * 
	 * The next line of code creates an empty string called encryptedProperties.
	 * This string will be used to store the encrypted properties.
	 * 
	 * The following lines of code iterate over the properties in the properties
	 * object. For each property, the code encrypts the value of the property and
	 * adds the encrypted value to the encryptedProperties string.
	 * 
	 * for (String key : properties.stringPropertyNames()) { 
	 * String value = properties.getProperty(key); byte[] encryptedValue =
	 * cipher.doFinal(value.getBytes()); encryptedProperties += key + "=" +
	 * Base64.encodeBase64String(encryptedValue) + "\n"; }
	 * 
	 * The stringPropertyNames() method returns an array of the names of the
	 * properties in the properties object. The getProperty() method returns the
	 * value of the property with the specified name. The doFinal() method encrypts
	 * the data and returns the encrypted data as a byte array. The
	 * Base64.encodeBase64String() method encodes the byte array as a Base64 string.
	 * 
	 * The final line of code returns the encryptedProperties string.
	 */

	public static String encryptProperties(String propertiesFilePath) throws Exception {
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertiesFilePath));

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("my-secret-key".getBytes(), "AES"));

		String encryptedProperties = "";
		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			byte[] encryptedValue = cipher.doFinal(value.getBytes());
			encryptedProperties += key + "=" + Base64.encodeBase64String(encryptedValue) + "\n";
		}

		return encryptedProperties;
	}

	/*
	 * This code first creates a Cipher object and initializes it with the
	 * encryption algorithm AES in ECB mode with PKCS5 padding. The init() method is
	 * used to initialize the Cipher object in decryption mode. The second parameter
	 * to the init() method is the secret key.
	 * 
	 * The next line of code creates an empty string called decryptedProperties.
	 * This string will be used to store the decrypted properties.
	 * 
	 * The following lines of code iterate over the lines in the encryptedProperties
	 * string. For each line, the code decrypts the value of the property and adds
	 * the decrypted value to the decryptedProperties string.
	 * 
	 * The split() method splits the encryptedProperties string into an array of
	 * strings, where each string is a line in the file. The split() method uses the
	 * \n character as the delimiter.
	 * 
	 * The doFinal() method decrypts the data and returns the decrypted data as a
	 * byte array. The new String() method converts the byte array to a string.
	 * 
	 * The final line of code returns the decryptedProperties string.
	 */
	public static String decryptProperties(String encryptedProperties, String secretKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey.getBytes(), "AES"));

		String decryptedProperties = "";
		String[] lines = encryptedProperties.split("\n");
		for (String line : lines) {
			String[] parts = line.split("=");
			String key = parts[0];
			String encryptedValue = parts[1];
			byte[] decryptedValue = cipher.doFinal(Base64.decodeBase64(encryptedValue));
			decryptedProperties += key + "=" + new String(decryptedValue) + "\n";
		}

		return decryptedProperties;
	}
}
