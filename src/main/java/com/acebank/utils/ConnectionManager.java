package com.acebank.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
		try {
//			Class.forName(DRIVER_NAME);
//			Class.forName("org.sqlite.JDBC");
//			Connection connection = DriverManager.getConnection("jdbc:sqlite:memory.db");

			// Load the H2 driver class
			Class.forName(Constants.H2_IN_MEMORY_DRIVER_NAME);
//			The Class.forName() method loads the H2 driver class into the Java runtime environment. This is necessary before you can connect to an H2 database.

			// Create a DB Connection
			Connection connection = DriverManager.getConnection(Constants.H2_IN_MEMORY_CONNECTION_URL);
//			Connection connection = DriverManager.getConnection(Constants.H2_TEST_DB_CONNECTION_URL);

			this.connection = connection;

			Statement statement = connection.createStatement();// Create a Statement

			// Execute Query 1 :: Create the default table of BANKUSERS
//			statement.execute(
//					"CREATE TABLE BANKUSERS ACCOUNT_NO INTEGER PRIMARY KEY, CUSTOMER_ID INTEGER NOT NULL,\r\n"
//							+ "  FIRST_NAME VARCHAR(255) NOT NULL,\r\n" + "  LAST_NAME VARCHAR(255) NOT NULL,\r\n"
//							+ "  AADHAR_NO VARCHAR(12) NOT NULL,\r\n" + "  EMAIL VARCHAR(255) NOT NULL,\r\n"
//							+ "  PASSWORD VARCHAR(255) NOT NULL,\r\n" + "  Balance INTEGER NOT NULL\r\n" + ");");
			statement.execute("CREATE TABLE BANKUSERS (ACCOUNT_NO INT PRIMARY KEY,"
					+ "FIRST_NAME VARCHAR(255) NOT NULL," + "LAST_NAME VARCHAR(255) NOT NULL,"
					+ "AADHAR_NO VARCHAR(12) NOT NULL," + "EMAIL VARCHAR(255) NOT NULL,"
					+ "PASSWORD VARCHAR(255) NOT NULL," + "BALANCE INT NOT NULL);");

			// Execute Query 2 :: Create the default table of TRANSACTIONS
//			statement.execute("CREATE TABLE TRANSACTIONS (\r\n" + "  ID INTEGER PRIMARY KEY,\r\n"
//					+ "  ACCOUNT1 INTEGER NOT NULL,\r\n" + "  ACCOUNT2 INTEGER,\r\n" + "  WITHDRAW INTEGER,\r\n"
//					+ "  DEPOSIT INTEGER,\r\n" + "  BALANCE INTEGER NOT NULL\r\n" + ");");
			statement.execute("CREATE TABLE TRANSACTIONS (ID INT AUTO_INCREMENT PRIMARY KEY," + "ACCOUNT1 INT NOT NULL,"
					+ "ACCOUNT2 INT," + "DEBIT INT," + "CREDIT INT," + "BALANCE INT NOT NULL);");

			// Execute Queries 4 :: Insert dummy users in BANKUSERS table
			statement.execute(
					"INSERT INTO BANKUSERS (ACCOUNT_NO,  FIRST_NAME, LAST_NAME, AADHAR_NO, EMAIL, PASSWORD, Balance)"
							+ "VALUES (123456,  'John', 'Doe', 123456789012, 'taliya1694@gmail.com', 'secret_password', 1000);");
			statement.execute(
					"INSERT INTO BANKUSERS (ACCOUNT_NO,  FIRST_NAME, LAST_NAME, AADHAR_NO, EMAIL, PASSWORD, Balance)"
							+ "VALUES (987654, 'Jane', 'Doe', 123456789013, 'janedoe@example.com', 'password_secret', 2000);");

			// Execute Queries 4 :: Insert dummy rows for users in TRANSACTIONS table
			statement.execute("INSERT INTO TRANSACTIONS ( ACCOUNT1, ACCOUNT2, DEBIT, CREDIT, BALANCE)"
					+ "VALUES ( 123456, 987654, 500, 0, 500);");
			statement.execute("update BANKUSERS set BALANCE=500 where ACCOUNT_NO = 123456;");

			statement.execute("INSERT INTO TRANSACTIONS ( ACCOUNT1, ACCOUNT2, DEBIT, CREDIT, BALANCE)\r\n"
					+ "VALUES ( 987654, 123456, 100, 0, 1900);");
			statement.execute("update BANKUSERS set BALANCE=2500 where ACCOUNT_NO = 987654;");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
