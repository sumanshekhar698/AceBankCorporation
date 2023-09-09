package com.acebank.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.acebank.dao.BankUserDao;
import com.acebank.models.BankUserModel;
import com.acebank.utils.MailUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;

/**
 * Servlet implementation class SignUP
 */
@Log
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		long aadharNumber = Long.parseLong(request.getParameter("aadharNumber"));
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		int accountNumber = ThreadLocalRandom.current().nextInt(10000000, 99999999); // Generate 10 digit random number

		HttpSession session = request.getSession();

		try {

			BankUserModel model = new BankUserModel();

			model.setAadharNumber(aadharNumber);
			model.setAccountNumber(accountNumber);
			model.setEmail(email);
			model.setPassword(password);
			model.setFirstName(firstName);
			model.setLastName(lastName);
			model.setBalance(0);

			BankUserDao userDao = new BankUserDao(model);

			if (userDao.signUp()) {

				String subject = "Welcome to AceBank";
				String msg = "Dear " + model.getFirstName() + "\n\nWe're thrilled to have you join the AceBank family."
						+ "We know you have a lot of choices when it comes to banks, "
						+ "so we're grateful that you chose us."
						+ "\nTo help you get started, here's your account number: " + model.getAccountNumber()
						+ ".\nPlease keep this number safe and confidential. You can use it to make deposits, withdrawals, and transfers."
						+ "\n\nWe also wanted to let you know about some of the benefits of being an AceBank customer. Here are a few of our favorites:"
						+ "\n- Free online banking and free ATM withdrawals at any of our ATMs"
						+ "\n- Low interest rates on loans and savings accounts and Personal banker support"
						+ "\n\nWe hope you'll take advantage of these benefits. And if you have any questions, please don't hesitate to contact us."
						+ "\nWe're here to help you achieve your financial goals. So welcome to AceBank! We're glad you're here."
						+ "\n\nSincerely," + "\nThe Ace Bank Team";

				MailUtil.sendMail(model.getEmail(), subject, msg);
				if (model.getBankUserTransactionDetailsModelList().isEmpty()) {
					session.setAttribute("transactionDetailsList", new ArrayList<Integer>());
				} else {
					session.setAttribute("transactionDetailsList", model.getBankUserTransactionDetailsModelList());
				}
				session.setAttribute("accountNumber", model.getAccountNumber());
				session.setAttribute("firstName", model.getFirstName());
				session.setAttribute("balance", model.getBalance());
				session.setAttribute("email", model.getEmail());

				response.sendRedirect("/AceBankCorporation/Home.jsp");
			} else {
				response.sendRedirect("GenericError.html");
			}

		} catch (IOException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect("GenericError.html");
		} catch (MessagingException e) {
			e.printStackTrace();
			response.sendRedirect("index.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
