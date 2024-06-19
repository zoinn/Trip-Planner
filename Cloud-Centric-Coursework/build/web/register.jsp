<%-- 
    Document   : register
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
        <h1>Registration</h1>
        <h2>If you have an account enter your previous name</h2>
    </head>
    <body>
        <form action = "registerDB.jsp" method= = "post">
            <table>
                <h1>Enter your username:</h1>
                <div><input type = "text", name = username ,value ="" placeholder = "Enter Name"></div>
            </table>
            <input type = "submit" value = "submit">
        </form>
    </body>
</html>
