<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Template</title>

<%
String uri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
 %>

<!-- Bootstrap Core CSS -->
<link href="<%=uri%>/resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link href="<%=uri%>/resources/vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,300italic,400italic,700italic"
	rel="stylesheet" type="text/css">
<link href="<%=uri%>/resources/vendor/simple-line-icons/css/simple-line-icons.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="<%=uri%>/resources/css/stylish-portfolio.min.css" rel="stylesheet">

</head>

<body id="page-top">

	<!-- Header -->
	<header class="masthead d-flex">
		<div class="container text-center my-auto">
			<h1 class="mb-1">Meeting Room: ${meetingRoom}</h1>
			<h3 class="mb-5">
				<em>${pax}</em><br/>
			</h3>
			<h3 class="mb-5">
				<em>${result1}</em><br/>
			</h3>
			<h3 class="mb-5">
				<em>Upcoming booking: </em><br/>
				<em>${result2}</em><br/>
			</h3>
		</div>
		<div class="overlay"></div>
	</header>

</body>