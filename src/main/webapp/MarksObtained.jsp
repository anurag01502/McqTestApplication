<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Marks Obtained</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f5f7fa, #c3cfe2);
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .container {
            background: #ffffff;
            padding: 40px 50px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            border-radius: 15px;
            text-align: center;
            max-width: 420px;
            width: 90%;
        }

        h1 {
            color: #2c3e50;
            margin-bottom: 25px;
        }

        p {
            font-size: 18px;
            color: #444;
            margin: 10px 0;
        }

        .highlight {
            color: #2980b9;
            font-weight: 600;
        }

        form {
            margin-top: 30px;
        }

        .btn {
            background-color: #2980b9;
            color: #fff;
            border: none;
            padding: 12px 25px;
            font-size: 16px;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .btn:hover {
            background-color: #1f6391;
        }
    </style>
</head>
<body>
<%
    String email = (String)session.getAttribute("Email__c");
    Integer total_marks = (Integer) session.getAttribute("totalMarks");
%>

<div class="container">
    <h1>Marks Summary</h1>
    <p>Email: <span class="highlight"><%=email %></span></p>
    <p>Total Marks: <span class="highlight"><%= total_marks %></span> / 46</p>

    <form action="GoToLeaderboard.jsp" method="post">
        <input type="submit" class="btn" value="Go To LeaderBoard">
    </form>
</div>

</body>
</html>
