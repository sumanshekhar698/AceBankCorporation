package com.acebank.utils;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import lombok.extern.java.Log;

@Log
public final class GetPropertiesFile {
	private static GetPropertiesFile getPropertiesFile;
	private static Properties properties;

	private GetPropertiesFile() {
		loadPropertiesFile();
	}

	public Properties getProperties() throws SQLException {
		if (properties != null)
			return properties;
		else {
			loadPropertiesFile();
			return properties;
		}
	}

	public static GetPropertiesFile getInstance() {
		if (getPropertiesFile == null) {
			getPropertiesFile = new GetPropertiesFile();
		}
		return getPropertiesFile;
	}

	private void loadPropertiesFile() {
		try (// Get the resource as a stream for PROPERTIES file.
				InputStream inputStreamForPropertiesFile = getClass().getResourceAsStream(Constants.PROPERTIES_FILE);) {
			// Create a properties object.
			Properties properties = new Properties();
			// Load the properties file into the properties object.
			properties.load(inputStreamForPropertiesFile);
			GetPropertiesFile.properties = properties;

		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("propertiesFileLoaded");

	}

}
