package com.acebank.controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.acebank.dao.BankUserDao;
import com.acebank.models.BankUserModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.java.Log;

/**
 * Servlet implementation class Login
 */

@Log
@WebServlet(name = "Login", urlPatterns = "/Login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

//	private Logger logger = Logger.getLogger(Login.class.getName());
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accountNumber = request.getParameter("accountNumber");
		String password = request.getParameter("password");

		try {
			BankUserModel bankUserModel = new BankUserModel();
//			bankUser.setCustomerId(customerId);
			bankUserModel.setAccountNumber(Integer.parseInt(accountNumber));
			bankUserModel.setPassword(password);
			log.info(bankUserModel.toString());

			BankUserDao userDao = new BankUserDao(bankUserModel);

			if (userDao.login()) {
				HttpSession session = request.getSession(true);
				session.setAttribute("accountNumber", bankUserModel.getAccountNumber());
				session.setAttribute("firstName", bankUserModel.getFirstName());
				session.setAttribute("balance", bankUserModel.getBalance());
				session.setAttribute("email", bankUserModel.getEmail());
				log.info(bankUserModel.toString());
				log.info(session.toString());

				if (userDao.statement().isEmpty()) {
					session.setAttribute("transactionDetailsList", new ArrayList<Integer>());
				} else {
					session.setAttribute("transactionDetailsList",
							bankUserModel.getBankUserTransactionDetailsModelList());
				}
//				response.sendRedirect("/AceBankCorporation/Home.jsp");
				response.sendRedirect("Home.jsp");

			} else {
				response.sendRedirect("LoginFail.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
