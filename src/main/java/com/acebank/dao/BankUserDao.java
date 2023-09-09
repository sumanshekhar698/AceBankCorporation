package com.acebank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.acebank.models.BankUserModel;
import com.acebank.models.BankUserTransactionDetailsModel;
import com.acebank.utils.ConnectionManager;

import lombok.extern.java.Log;

@Log
public class BankUserDao {

	private BankUserModel bankUserModel;
	private Connection connection;

	public BankUserDao() throws SQLException {
		connection = ConnectionManager.getInstance().getConnection();
	}

	public BankUserDao(BankUserModel model) throws SQLException {
		connection = ConnectionManager.getInstance().getConnection();
		this.bankUserModel = model;
	}

	/**
	 * login() used in the Login Servlet class This is used to check if the customer
	 * ID and password are correct. It will also get the required fields which will
	 * be used in the current session.
	 * 
	 * @return true if the query is successful
	 * @throws SQLException
	 */

	public boolean login() throws SQLException {
		String s = "select FIRST_NAME, LAST_NAME, EMAIL, BALANCE from BANKUSERS  where ACCOUNT_NO=? and PASSWORD=?";
		PreparedStatement pstmt = connection.prepareStatement(s);

		pstmt.setLong(1, bankUserModel.getAccountNumber());
		pstmt.setString(2, bankUserModel.getPassword());
		ResultSet res = pstmt.executeQuery();

		if (res.next() == true) {
			bankUserModel.setFirstName(res.getString(1));
			bankUserModel.setLastName(res.getString(2));
			bankUserModel.setEmail(res.getString(3));
			bankUserModel.setBalance(res.getInt(4));
			log.info(bankUserModel.toString());
			return true;
		}
		return false;
	}

	public List<BankUserTransactionDetailsModel> statement() throws SQLException {
		String statement = "select * from TRANSACTIONS where ACCOUNT1=? order by id desc";
		PreparedStatement pstmt = connection.prepareStatement(statement);
		pstmt.setInt(1, bankUserModel.getAccountNumber());

		ResultSet res = pstmt.executeQuery();
		while (res.next()) {

			bankUserModel.getBankUserTransactionDetailsModelList().add(new BankUserTransactionDetailsModel(
					res.getInt(2), res.getInt(3), res.getInt(4), res.getInt(5), res.getInt(6)));
		}

		return bankUserModel.getBankUserTransactionDetailsModelList();
	}

	public boolean closeDBConnection() throws SQLException {
		if (!this.connection.isClosed()) {
			this.connection.close();
			return true;
		}
		return false;
	}

	/**
	 * forgotPassword() is used in the Forgot Servlet class. This method will use a
	 * valid email address entered by the user to get the password, tehn account
	 * number and password will then be sent to that email address.
	 * 
	 * @return true if the query is successful
	 * @throws SQLException
	 */

	public boolean forgotPassword() throws SQLException {

		String s = "select FIRST_NAME, ACCOUNT_NO,PASSWORD from BANKUSERS where EMAIL =?";
		PreparedStatement pstmt = connection.prepareStatement(s);
		pstmt.setString(1, this.bankUserModel.getEmail());
		ResultSet res = pstmt.executeQuery();
		if (res.next()) {
			this.bankUserModel.setFirstName(res.getString(1));
			this.bankUserModel.setAccountNumber(Integer.parseInt(res.getString(2)));
			this.bankUserModel.setPassword(res.getString(3));
			return true;
		}
		return false;
	}

	/**
	 * signup() is used in the SignUp Servlet class Used to make a new entry in the
	 * database for the new User Balance is set to 0 for the new user
	 * 
	 * @return true if the query is successful
	 * @throws SQLException
	 */

	public boolean signUp() throws SQLException {

		String s = "insert into BANKUSERS (ACCOUNT_NO, FIRST_NAME, LAST_NAME, AADHAR_NO, EMAIL, PASSWORD, " + "Balance)"
				+ "values (?,?,?,?,?,?,?)";

		PreparedStatement pstmt = connection.prepareStatement(s);

		pstmt.setInt(1, bankUserModel.getAccountNumber());
		pstmt.setString(2, bankUserModel.getFirstName());
		pstmt.setString(3, bankUserModel.getLastName());
		pstmt.setLong(4, bankUserModel.getAadharNumber());
		pstmt.setString(5, bankUserModel.getEmail());
		pstmt.setString(6, bankUserModel.getPassword());
		pstmt.setInt(7, 0); // Set balance to 0 for new accounts

		int x = pstmt.executeUpdate();
		return x > 0;

	}

	public boolean changePassword(String newPassword) throws SQLException {

		String s1 = "select PASSWORD from BANKUSERS where ACCOUNT_NO = ?";
		PreparedStatement pstmt1 = connection.prepareStatement(s1);
		pstmt1.setInt(1, this.bankUserModel.getAccountNumber());

		ResultSet res = pstmt1.executeQuery();
		if (res.next()) {
			String currentPassword = res.getString(1);
			if (!currentPassword.equals(bankUserModel.getPassword()))
				return false;
		}

		var s2 = "update BANKUSERS set PASSWORD = ? where ACCOUNT_NO = ?";

		PreparedStatement pstmt2 = this.connection.prepareStatement(s2);
		pstmt2.setString(1, newPassword);
		pstmt2.setInt(2, this.bankUserModel.getAccountNumber());

		var x = pstmt2.executeUpdate();
		log.info("changedPassword :: ?" + x);
		return x > 0;
	}

	/**
	 * Used in Home Servlet deposit() is used to set the balance amount for the
	 * current user.
	 * 
	 * @return true if the query is successful
	 * @throws SQLException
	 */

	public boolean deposit() throws SQLException {

		String s = "select BALANCE from BANKUSERS  where ACCOUNT_NO=?";
		PreparedStatement pstmt = connection.prepareStatement(s);
		pstmt.setLong(1, bankUserModel.getAccountNumber());
		ResultSet res = pstmt.executeQuery();

		if (res.next() == true) {
			this.bankUserModel.setBalance(res.getInt(1));// fetching the balance and setting it to model
		}

		String queryUpdateBalanceOfBankUsers = "update BANKUSERS set BALANCE=? where ACCOUNT_NO = ?";
		pstmt = connection.prepareStatement(queryUpdateBalanceOfBankUsers);

		int balance = this.bankUserModel.getBalance();
		int depositAmount = this.bankUserModel.getDepositAmount();

		balance += depositAmount;
		pstmt.setInt(1, balance);
		pstmt.setInt(2, this.bankUserModel.getAccountNumber());
		int updateBalance = pstmt.executeUpdate();

//		int id = getID() + 1;

		String queryInsertTransactionEntryForUserDeposit = "INSERT INTO TRANSACTIONS ( ACCOUNT1, ACCOUNT2, CREDIT, BALANCE) VALUES ( ?, ?, ?, ?);";
		pstmt = this.connection.prepareStatement(queryInsertTransactionEntryForUserDeposit);

		pstmt.setInt(1, this.bankUserModel.getAccountNumber());
		pstmt.setInt(2, this.bankUserModel.getAccountNumber()); // Since its from the same account
		pstmt.setInt(3, depositAmount);
		pstmt.setInt(4, balance);
		int executeUpdate = pstmt.executeUpdate();
		if (executeUpdate > 0)
			log.info("Updated Transaction table for Deposit");
		else
			log.warning("Failed to Updated Transaction table for Deposit");

		this.bankUserModel.setBalance(balance); // update balance

		return updateBalance > 0;
	}

	/**
	 * Used in Home Servlet
	 * 
	 * transfer() is used to send the amount from the current user's balance to
	 * another user's balance
	 * 
	 * @return true if successful
	 * @throws SQLException
	 */
	public boolean transfer() throws SQLException {

		/*
		 * Subtract balance from the sender's account (Account1) first and update its
		 * entry in transaction table
		 */

		String s = "select BALANCE from BANKUSERS  where ACCOUNT_NO=?";
		PreparedStatement pstmt = connection.prepareStatement(s);

		pstmt.setLong(1, bankUserModel.getAccountNumber());
		ResultSet res = pstmt.executeQuery();

		log.info(bankUserModel.toString());

		if (res.next() == true) {
			bankUserModel.setBalance(res.getInt(1));// fetching the balance and setting it to model
		}
		int balanceOfAccount1 = bankUserModel.getBalance();
		int debitAmountFromAccount1 = bankUserModel.getTransferAmount();

		if (balanceOfAccount1 < debitAmountFromAccount1) {// checking for enough funds to transfer
			log.warning("insufficient funds for transfer");
			return false;
		}

		String queryUpdateBalanceOfBankUsers = "update BANKUSERS set BALANCE=? where ACCOUNT_NO = ?";

		balanceOfAccount1 -= debitAmountFromAccount1; // Subtract the transfer amount with the current balance
		bankUserModel.setBalance(balanceOfAccount1);

		pstmt = connection.prepareStatement(queryUpdateBalanceOfBankUsers);
		pstmt.setInt(1, balanceOfAccount1);
		pstmt.setInt(2, bankUserModel.getAccountNumber());
		int updateAccount1 = pstmt.executeUpdate();

//		String table2 = "insert into TRANSACTIONS values (?,?,?,?,?,?)";
		String queryInsertTransactionEntryForAccount1 = "INSERT INTO TRANSACTIONS ( ACCOUNT1, ACCOUNT2, DEBIT, BALANCE) VALUES ( ?, ?, ?, ?);";

		pstmt = this.connection.prepareStatement(queryInsertTransactionEntryForAccount1);
		pstmt.setInt(1, this.bankUserModel.getAccountNumber());
		pstmt.setInt(2, this.bankUserModel.getTransferToAccount());
		pstmt.setInt(3, debitAmountFromAccount1);
		pstmt.setInt(4, balanceOfAccount1);
		int insertIntoTransactionForAccount1 = pstmt.executeUpdate();
		if (insertIntoTransactionForAccount1 > 0)
			log.info("Updated Transaction table for Account1");
		else
			log.warning("Failed to Updated Transaction table for Account1");

		/*
		 * Add balance to the reviever's account (Account2) first and update its entry
		 * in transaction table
		 */

		if (updateAccount1 > 0) {// only execute if the money has been debited from account1 (sender)
			String queryGetBalanceFromBankUsersAccount2 = "select balance from BANKUSERS where ACCOUNT_NO = ?";
			pstmt = connection.prepareStatement(queryGetBalanceFromBankUsersAccount2);
			pstmt.setInt(1, bankUserModel.getTransferToAccount());
			res = pstmt.executeQuery();

			if (res.next()) {
				int balanceOfAccount2 = res.getInt(1); // Adding
				balanceOfAccount2 += debitAmountFromAccount1;

				pstmt = connection.prepareStatement(queryUpdateBalanceOfBankUsers);
				pstmt.setInt(1, balanceOfAccount2);
				pstmt.setInt(2, bankUserModel.getTransferToAccount());
				int updateAccount2 = pstmt.executeUpdate();

				String queryInsertTransactionEntryForAccount2 = "INSERT INTO TRANSACTIONS ( ACCOUNT1, ACCOUNT2, CREDIT, BALANCE) VALUES ( ?, ?, ?, ?);";
				pstmt = connection.prepareStatement(queryInsertTransactionEntryForAccount2);
				pstmt.setInt(1, bankUserModel.getTransferToAccount());
				pstmt.setInt(2, bankUserModel.getAccountNumber());
				pstmt.setInt(3, debitAmountFromAccount1);
				pstmt.setInt(4, balanceOfAccount2);

				int insertIntoTransactionForAccount2 = pstmt.executeUpdate();
				if (insertIntoTransactionForAccount2 > 0)
					log.info("Updated Transaction table fro Account2");
				else
					log.warning("Failed to Updated Transaction table fro Account2");

				return updateAccount2 > 0;
			} else {
				log.info("to Account Not Available");
				return false;
			}
		}
		return false;
	}

}
