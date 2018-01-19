<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Meeting Room Booking System</title>

	<style>
		tr:first-child{
			font-weight: bold;
			background-color: #C6C9C4;
		}
	</style>

</head>


<body>
	<h2>List of Meeting Room</h2>
	<table>
		<tr>
			<td>Event Start Date</td><td>Event Start Time</td><td>Event End Date</td><td>Event End Time</td><td>Status</td><td>Event Date Created</td><td>User</td><td>Event Id</td><td></td>
		</tr>
		<c:forEach items="${meetingRooms}" var="meetingRoom">
			<tr>
			<td>${meetingRoom.eventStartDate}</td>
			<td>${meetingRoom.eventStartTime}</td>
			<td>${meetingRoom.eventEndDate}</td>
			<td>${meetingRoom.eventEndTime}</td>
			<td>${meetingRoom.status}</td>
			<td>${meetingRoom.eventDateCreated}</td>
			<td>${meetingRoom.user}</td>
			<td><a href="<c:url value='/edit-${meetingRoom.eventId}-meetingRoom' />">${meetingRoom.eventId}</a></td>
			<td><a href="<c:url value='/delete-${meetingRoom.eventId}-meetingRoom' />">delete</a></td>
			</tr>
		</c:forEach>
	</table>
	<br/>
	<a href="<c:url value='/new' />">Add New Entry</a>
</body>
</html>