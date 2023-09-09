<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.acebank.models.BankUserTransactionDetailsModel"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/AceBankCorporation/css/navbar-home.css">
<link rel="stylesheet" href="/AceBankCorporation/css/home.css">
<title>Home</title>
</head>
<%
session = request.getSession();
String firstName = (String) session.getAttribute("firstName");
int accountNumber = (int) session.getAttribute("accountNumber");
int balance = (int) session.getAttribute("balance");
%>
<body>
	<header>
		<h1 class="logo-text">
			Ace<span class="bank">Bank</span>
		</h1>
		<!-- Navigation toggle -->
		<input type="checkbox" id="nav-toggle" class="nav-toggle"> <label
			for="nav-toggle" class="nav-toggle-label"> <img
			src="/AceBank/images/menu-24px.svg">
		</label>
		<nav>
			<ul>

				<!-- Using Account.html instead of Account, since it would crash with an sql exception if used directly;
					 The class would directly execute without waiting for the form otherwise -->

				<li><a href="ChangePassword.html">Change Password</a></li>
				<li><a href="Logout" class="login-btn">Logout</a></li>
			</ul>
		</nav>
	</header>
	<div class="toolbar-space"></div>
	<main class="main-container">
		<div class="title-container">
			<h1>
				Hello,<%
			out.print(firstName);
			%>.
			</h1>
			<h3>
				Account Number:
				<%
			out.print(accountNumber);
			%>
			</h3>
		</div>
		<div class="balance-transfer-container">

			<div class="balance">
				<h2>Your Total Balance</h2>
				<h1>
					<span>₹</span> ${balance}
				</h1>
				<div class="buttons">
					<form action="Home" method="post">
						<input type="text" name="deposit" placeholder="Deposit" /> <input
							type="submit" name="depositButton" value="Deposit" />
					</form>
					<a href="LoanOptions.jsp">Get Loans</a>
				</div>
			</div>
			<!--  -->
			<div class="transfer">
				<h2>Transfer Amount</h2>
				<form action="Home" method="post">
					<div>
						<!-- <label>To</label> -->
						<input type="text" name="toAccount" placeholder="Account Number" />
						<!-- <label>Amount</label> -->
						<input type="text" name="toAmount" placeholder="Amount" />
					</div>
					<input type="submit" value="Send" />
				</form>
			</div>
			<!--  -->
		</div>
		<!--  -->
		<section class="statement">
			<h2>Transactions</h2>
			<div class="fake-table">
				<table class="content-table">
					<thead>
						<tr>
							<th>Account1</th>
							<th>Account2</th>
							<th>Credit</th>
							<th>Debit</th>
							<th>Balance</th>
						</tr>
					</thead>
					<%
					List<BankUserTransactionDetailsModel> transactionDetailsList = (List<BankUserTransactionDetailsModel>) session
							.getAttribute("transactionDetailsList");
					for (BankUserTransactionDetailsModel transaction : transactionDetailsList) {
					%>
					<tbody>
						<tr>
							<td><%=transaction.getAccount1()%></td>
							<td><%=transaction.getAccount2()%></td>
							<td><%=transaction.getCredit()%></td>
							<td><%=transaction.getDebit()%></td>
							<td><%=transaction.getBalance()%></td>
						</tr>
					</tbody>
					<%
					}
					%>
				</table>

			</div>
		</section>
	</main>
</body>
</html>