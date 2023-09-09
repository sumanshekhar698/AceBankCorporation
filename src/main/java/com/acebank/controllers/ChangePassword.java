package com.acebank.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.acebank.dao.BankUserDao;
import com.acebank.models.BankUserModel;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;

@Log
@WebServlet(name = "ChangePassword", urlPatterns = "/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		HttpSession session = request.getSession();
		log.info(session.toString());
		log.info("Account Number" + (int) session.getAttribute("accountNumber"));

		try {
			BankUserModel model = new BankUserModel();
			model.setPassword(currentPassword);
			model.setAccountNumber((int) session.getAttribute("accountNumber"));
			BankUserDao userDao = new BankUserDao(model);

			if (userDao.changePassword(newPassword)) {
//				response.sendRedirect("Home.jsp");
				log.info("Password Changed and did the Logout");
				RequestDispatcher rd = request.getRequestDispatcher("Logout");// servlet chaining

				// Logout is the url-pattern of the Logout servlet
				rd.forward(request, response);// method may be include or forward
				log.info("Password Changed and did the Logout");

			} else {
				log.info("Redirecting to Error page");
				response.sendRedirect("GenericError.html");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("Redirected to Fail Page from SQLException block MSG :: " + e.getMessage());
			response.sendRedirect("GenericError.html");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * @Override protected void doError(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException { //
	 * Prevent the error log from being displayed
	 * request.setAttribute("javax.servlet.error.sendErrorOnException",
	 * Boolean.FALSE);
	 * 
	 * // Handle the error // ... }
	 */

	protected void doError(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Prevent the error log from being displayed
		request.setAttribute("javax.servlet.error.sendErrorOnException", Boolean.FALSE);

		// Handle the error
		// ...
	}

}
