
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Email Manager</title>
</head>
<body>
    <h1>Email Manager</h1>

    <!-- Display the list of email addresses -->
   <h2>Email Addresses:</h2>
    <ul>
        <c:forEach var="email" items="${emailAddresses}">
            <li>${email}</li>
        </c:forEach>
    </ul>
   

    <!-- Form for adding or deleting email addresses -->
    <form action="EmailServlet" method="post">
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" required>
        <button type="submit" name="action" value="Subscribe">Subscribe</button>
        <button type="submit" name="action" value="Delete">Delete</button>
    </form>
</body>
</html>
