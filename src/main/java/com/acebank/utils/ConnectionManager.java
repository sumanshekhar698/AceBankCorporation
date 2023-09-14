package com.acebank.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

import lombok.extern.java.Log;

@Log
public final class ConnectionManager {
	private static ConnectionManager connectionManager;
	private Connection connection;

	private ConnectionManager() {
		establishH2InMemoryDBConnection();
	}

	public Connection getConnection() throws SQLException {
//		return DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
		if (!connection.isClosed())
			return connection;
		else {
			establishH2InMemoryDBConnection();
			return connection;
		}
	}

	public static ConnectionManager getInstance() {
		if (connectionManager == null) {
			connectionManager = new ConnectionManager();
		}
		return connectionManager;
	}

	private void establishH2InMemoryDBConnection() {
		try (// log.info(propertyValueContainingPathForH2Script);
				InputStream inputStreamForSQLH2Script = getClass().getResourceAsStream(GetPropertiesFile.getInstance()
						.getProperties().getProperty(Constants.PROPERTY_NAME_FOR_H2_SCRIPT));) {

			// Load the H2 driver class
			Class.forName(Constants.H2_IN_MEMORY_DRIVER_NAME);
			/*
			 * The Class.forName() method loads the H2 driver class into the Java runtime
			 * environment. This is necessary before you can connect to an H2 database.
			 */
			
			// Create a DB Connection
			Connection connection = DriverManager.getConnection(Constants.H2_IN_MEMORY_CONNECTION_URL);
			log.info("Established DB connection");
			this.connection = connection;

			ScriptRunner scriptRunner = new ScriptRunner(connection);// Create a ScriptRunner object.

			// Execute the .sql script.
//			scriptRunner.runScript(new BufferedReader(new FileReader("/src/main/java/h2_loader.sql")));
			scriptRunner.runScript(new BufferedReader(new InputStreamReader(inputStreamForSQLH2Script)));
			log.info("Executed SQL Script");

//			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
