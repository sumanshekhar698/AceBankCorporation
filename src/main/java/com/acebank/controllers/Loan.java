package com.acebank.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.acebank.utils.MailUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;

@Log
@WebServlet(name = "Loan", urlPatterns = "/Loan")
public class Loan extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(Loan.class.getName());

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String parameterLoanType = request.getParameter("loanType");// we can only pass String data via URL parameters
		log.info("Loan Type parameters :: " + request.getParameter("loanType"));
		String firstName = (String) session.getAttribute("firstName");
		String email = (String) session.getAttribute("email");
		log.info("EMAIL :: " + email + "Name " + firstName);

		String subject = "Loan Application Received";
		String body = "Dear " + firstName + "\n\nThank you for applying for a " + parameterLoanType
				+ " loan with Ace Bank. We have received your request and will be reviewing it shortly."
				+ "\nWe will be in touch with you as soon as we have a decision."
				+ "\nWe appreciate your banking with us." + "\n\nSincerely," + "\nThe Ace Bank Team";

		try {
			MailUtil.sendMail(email, subject, body);
			response.sendRedirect("LoanThankYou.jsp");

		} catch (MessagingException e) {
			e.printStackTrace();
			log.warning("Mail Error !! for sending email to :: " + email);
			response.sendRedirect("GenericError.html");
		}

	}

}
