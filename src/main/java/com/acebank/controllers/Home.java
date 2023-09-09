package com.acebank.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

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
 * Servlet implementation class Home
 */
@Log
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		int toAccount = 0;
		int toAmount = 0;
		int accountNumber = (int) session.getAttribute("accountNumber");

		// AMOUNT DEPOSIT
		if (!StringUtils.isEmpty(request.getParameter("deposit"))
				&& Integer.parseInt(request.getParameter("deposit")) > 0) {
			log.info("Amount DEPOSIT initiated");

			int addToBalance = Integer.parseInt(request.getParameter("deposit"));
			BankUserModel model = new BankUserModel();
			model.setDepositAmount(addToBalance);
			model.setAccountNumber(accountNumber);
			try {
				BankUserDao userDao = new BankUserDao(model);
				if (userDao.deposit()) {// DEPOSIT STATUS CHECK
					log.info("Amount deposited");

					if (userDao.statement().isEmpty()) {// updating latest STATEMENTS
						session.setAttribute("transactionDetailsList", new ArrayList<Integer>());
					} else {
						session.setAttribute("transactionDetailsList", model.getBankUserTransactionDetailsModelList());
					}

//					addToTransactionDetails(userDao, session, model);
					// Update balance
//					request.setAttribute("balance", model.getBalance());
					session.setAttribute("balance", model.getBalance());
					response.sendRedirect("Home.jsp");
//					request.getRequestDispatcher("Home.jsp").forward(request, response);
					/*
					 * The code request.getRequestDispatcher("/Home.jsp").forward(request, response)
					 * is used to forward a request from a servlet to another resource, such as a
					 * JSP page or HTML file. The request.getRequestDispatcher() method returns a
					 * RequestDispatcher object, which can be used to forward the request. The
					 * forward() method of the RequestDispatcher object sends the request to the
					 * specified resource.
					 * 
					 * In this case, the request is being forwarded to the JSP page /Home.jsp. The
					 * request and response objects are passed to the forward() method so that the
					 * next resource can access them.
					 * 
					 * The forward() method is different from the sendRedirect() method. The
					 * sendRedirect() method sends a redirect response to the client, which means
					 * that the client will make a new request to the specified resource. The
					 * forward() method, on the other hand, does not send a redirect response to the
					 * client. Instead, the request is forwarded to the specified resource and the
					 * client will continue to interact with the current servlet.
					 * 
					 * The forward() method is typically used when you want to pass data from one
					 * servlet to another. For example, you could use the forward() method to pass
					 * the results of a calculation from one servlet to another servlet that will
					 * display the results.
					 */
				}
			} catch (SQLException e) {
				e.printStackTrace();
				log.info("deposit failed");
//				request.getRequestDispatcher("/Home.jsp").forward(request, response);//continues with teh same request so migh cause bugs
				response.sendRedirect("Home.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (!StringUtils.isEmpty(request.getParameter("toAccount"))
				&& !StringUtils.isEmpty(request.getParameter("toAmount"))
				&& Integer.parseInt(request.getParameter("toAmount")) > 0) {// TRANSFER AMOUNT
			log.info("Amount TRANSFER initiated");
			toAccount = Integer.parseInt(request.getParameter("toAccount"));
			session.setAttribute("toAccount", toAccount);
			toAmount = Integer.parseInt(request.getParameter("toAmount"));
			session.setAttribute("toAmount", toAmount);

			BankUserModel model = new BankUserModel();
			model.setAccountNumber(accountNumber);
			model.setTransferToAccount((int) session.getAttribute("toAccount"));
			model.setTransferAmount((int) session.getAttribute("toAmount"));

			try {
				BankUserDao userDao = new BankUserDao(model);

				if (userDao.transfer()) {// TRANSFER STATUS CHECK

					if (userDao.statement().isEmpty()) {// updating latest STATEMENTS
						session.setAttribute("transactionDetailsList", new ArrayList<Integer>());
					} else {
						session.setAttribute("transactionDetailsList", model.getBankUserTransactionDetailsModelList());
					}

//						request.setAttribute("balance", model.getBalance());
					session.setAttribute("balance", model.getBalance());
					request.getRequestDispatcher("Home.jsp").forward(request, response);
				} else {
					log.info("transfer failed");
					response.sendRedirect("Home.jsp");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				log.info("transfer failed");
				response.sendRedirect("Home.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			response.sendRedirect("Home.jsp");
		}

	}

}
