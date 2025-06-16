<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Info with Ranking</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(to right, #d7e1ec, #f0f4f8);
            margin: 0;
            padding: 20px;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 30px;
            font-size: 28px;
        }

        .table-container {
            max-width: 90%;
            margin: 0 auto;
            overflow-x: auto;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
            padding: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 16px;
        }

        th, td {
            padding: 14px 18px;
            text-align: center;
        }

        th {
            background-color: #007bff;
            color: #fff;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #e6f2ff;
            transition: background 0.3s ease;
        }

        td {
            color: #333;
        }

        @media screen and (max-width: 768px) {
            table {
                font-size: 14px;
            }
        }
    </style>
</head>
<body>

<%
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sql = "SELECT Username__c, Email__c, marks, RANK() OVER (ORDER BY marks DESC) AS `rank` FROM UsersInfoInJavaTest";
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaTestDB", "root", "@Nu07011528");
        pstmt = con.prepareStatement(sql);
        rs = pstmt.executeQuery();
%>

    <h2>Leaderboard - User Ranking</h2>
    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Marks</th>
                    <th>Rank</th>
                </tr>
            </thead>
            <tbody>
<%
        while (rs.next()) {
%>
                <tr>
                    <td><%= rs.getString("Username__c") %></td>
                    <td><%= rs.getString("Email__c") %></td>
                    <td><%= rs.getInt("marks") %></td>
                    <td><%= rs.getInt("rank") %></td>
                </tr>
<%
        }
    } catch (Exception e) {
%>
        <p style="color: red; text-align: center;">Error: <%= e.getMessage() %></p>
<%
    } finally {
        if (rs != null) try { rs.close(); } catch (Exception ignored) {}
        if (pstmt != null) try { pstmt.close(); } catch (Exception ignored) {}
        if (con != null) try { con.close(); } catch (Exception ignored) {}
    }
%>
            </tbody>
        </table>
    </div>

</body>
</html>
