<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="./css/navbar-home.css">
<link rel="stylesheet" type="text/css" href="./css/loan.css">
<title>Thank You</title>
</head>

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
				<li><a href="Home.jsp">Dashboard</a></li>
				<!-- <li><a href="#">Account</a></li> -->
				<li><a href="Logout" class="login-btn">Logout</a></li>
			</ul>
		</nav>
	</header>
	<div class="toolbar-space"></div>
	<%
	session = request.getSession();
	String email = (String) session.getAttribute("email");
	%>

	<main>
		<section class="title">
			<h1>Thanks for the loan request</h1>
			<p>
				We have sent a confirmation mail to "<%
			out.print(email);
			%>". Our team will shortly get back to you.<br> Thanks for
				banking with us :)
			</p>
		</section>
	</main>
</body>

</html>