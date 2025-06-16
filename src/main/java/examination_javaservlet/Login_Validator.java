package examination_javaservlet;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

@WebServlet("/Login_Validator")
public class Login_Validator extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static HttpSession session;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = null;
        PreparedStatement ptsm = null;
        ResultSet rs = null;
         session = request.getSession();
        
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connect to database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaTestDB", "root", "@Nu07011528");

            // SQL: Use actual table name instead of database name
            String sql = "SELECT Email__c, Password__c FROM UsersInfoInJavaTest WHERE Email__c = ? AND password__c = ?";
            ptsm = con.prepareStatement(sql);
            ptsm.setString(1, request.getParameter("email"));
            ptsm.setString(2, request.getParameter("password"));
            
           
            rs = ptsm.executeQuery();

            if (rs.next()) {
                // Credentials valid
            	 session.setAttribute("Email__c",request.getParameter("email"));
            	 
                session.setAttribute("Password__c", request.getParameter("password"));
                
                // Redirect to success page (e.g., welcome.jsp)
                response.sendRedirect("Instructions.html");
            } else {
                // Invalid credentials
                RequestDispatcher rqdp = request.getRequestDispatcher("InvalidLoginCredentials.html");
                rqdp.include(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // You might want to log this instead or show a generic error page
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ptsm != null) ptsm.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}
