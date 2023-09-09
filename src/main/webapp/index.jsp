<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en">

<head>
<!-- <meta charset="ISO-8859-1"> -->
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<!-- <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet"> -->

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/remixicon/3.5.0/remixicon.min.css"
	integrity="sha512-/VYneElp5u4puMaIp/4ibGxlTd2MV3kuUIroR3NSQjS2h9XKQNebRQiyyoQKeiGE9mRdjSCIZf9pb7AVJ8DhCg=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />

<link rel="stylesheet" type="text/css" href="./css/navbar.css">
<link rel="stylesheet" type="text/css" href="./css/landing.css">
<title>Ace Bank</title>
</head>


<div class="container">

	<header>
		<h1 class="logo-text">
			Ace<span class="bank">Bank</span>
		</h1>
		<!-- Navigation toggle -->
		<input type="checkbox" id="nav-toggle" class="nav-toggle"> <label
			for="nav-toggle" class="nav-toggle-label"> <i
			class="ri-menu-5-line"></i>

		</label>
		<nav>
			<ul>
				<li><a href="#">Features</a></li>
				<li><a href="#">About</a></li>
				<li><a href="#">FAQ</a></li>
				<li><a href="Login.html" class="login-btn">Login</a></li>
			</ul>
		</nav>
	</header>
	<div class="toolbar-space"></div>

	<main class="content">
		<section class="main-content">
			<h1>
				Banking Made <span class="easy">Easy</span>
			</h1>
			<p>40,000 people open an Ace bank account every week to spend,
				save and manage their money. Join them in less than 10 minutes.</p>

			<a href="SignUp.html">Open Account</a>
		</section>
		<figure>
			<img src="./images/support-team.png" alt="illustration of users">
		</figure>
	</main>
</div>
</body>

</html>