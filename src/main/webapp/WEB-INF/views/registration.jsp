<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Meeting Room Booking Entry Form</title>

<style>

	.error {
		color: #ff0000;
	}
</style>

</head>

<body>

	<h2>Meeting Room Booking Entry Form</h2>
 
	<form:form method="POST" modelAttribute="meetingRoom">
		<form:input type="hidden" path="id" id="id"/>
		<table>
			<tr>
				<td><label for="eventStartDate">Event Start Date: </label> </td>
				<td><form:input path="eventStartDate" id="eventStartDate"/></td>
				<td><form:errors path="eventStartDate" cssClass="error"/></td>
		    </tr>

			<tr>
				<td><label for="eventStartTime">Event Start Time: </label> </td>
				<td><form:input path="eventStartTime" id="eventStartTime"/></td>
				<td><form:errors path="eventStartTime" cssClass="error"/></td>
		    </tr>
		    
		    <tr>
				<td><label for="eventEndDate">Event End Date: </label> </td>
				<td><form:input path="eventEndDate" id="eventEndDate"/></td>
				<td><form:errors path="eventEndDate" cssClass="error"/></td>
		    </tr>
		    
		    <tr>
				<td><label for="eventEndTime">Event End Time: </label> </td>
				<td><form:input path="eventEndTime" id="eventEndTime"/></td>
				<td><form:errors path="eventEndTime" cssClass="error"/></td>
		    </tr>
		    
		    <tr>
				<td><label for="user">User: </label> </td>
				<td><form:input path="user" id="user"/></td>
				<td><form:errors path="user" cssClass="error"/></td>
		    </tr>
		    
		    <tr>
				<td><label for="eventId">Event Id: </label> </td>
				<td><form:input path="eventId" id="eventId"/></td>
				<td><form:errors path="eventId" cssClass="error"/></td>
		    </tr>
	
			<tr>
				<td colspan="3">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Update"/>
						</c:when>
						<c:otherwise>
							<input type="submit" value="Register"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</form:form>
	<br/>
	<br/>
	Go back to <a href="<c:url value='/list' />">List of All Booking</a>
</body>
</html>