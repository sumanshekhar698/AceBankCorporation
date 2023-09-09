package com.acebank.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import com.acebank.dao.BankUserDao;
import com.acebank.models.BankUserModel;
import com.acebank.utils.MailUtil;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;

@Log
@WebServlet(name = "Forgot", urlPatterns = "/Forgot")
public class Forgot extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private static final String EMAIL_ADDRESS = "ace.bank.dev@gmail.com";
//	private static final String PASSWORD = "aPPLEfresh*8";

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		log.info("EMAIL :: " + email);
//		HttpSession session = request.getSession();
//		session.setAttribute("email", email);

		BankUserModel model = new BankUserModel();
		model.setEmail(email);
		try {
			BankUserDao userDao = new BankUserDao(model);
			if (userDao.forgotPassword()) {
				String subject = "Account Recovery";
				String msg = "Hi " + model.getFirstName()
						+ "\n\nWe received a request for account and password recovery for your account."
						+ "\nYour credentials are: " + "\n-Account Number: " + model.getAccountNumber()
						+ "\n-Password: " + model.getPassword()
						+ "\nIf you did not request this, please ignore this email and login to our webapp to change your password immediately"
						+ "\n\nWe appreciate your banking with us." + "\n\nSincerely," + "\nThe Ace Bank Team";

				MailUtil.sendMail(email, subject, msg);
				response.sendRedirect("Login.html");
			} else {
				response.sendRedirect("/AceBank/Forgot-Fail.html");
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			response.sendRedirect("GenericError.html");
		} catch (MessagingException e) {
			e.printStackTrace();
			log.warning("Mail Error !! for sending email to :: " + email);
			response.sendRedirect("GenericError.html");
		}

	}

}
