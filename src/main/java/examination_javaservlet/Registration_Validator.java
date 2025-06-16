package examination_javaservlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.sql.*;

@WebServlet("/Registration_Validator")
public class Registration_Validator extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try 
        {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Retrieve form data from request
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm_password");

            // Establish database connection
            
            String validationQuery = "select *  from UsersInfoInJavaTest where Email__c=?";
            
            
            
            
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/JavaTestDB", "root", "@Nu07011528"
            );
            
            PreparedStatement ptms1= con.prepareStatement(validationQuery);
            ptms1.setString(1,email);
            ResultSet rsvalidation = ptms1.executeQuery();
            
            
            	if(rsvalidation.next())
            	{
            	
            		//response.setContentType("text/html");
            	
            	
            		RequestDispatcher redp = request.getRequestDispatcher("AlreadyExists.html");
            	
            		redp.forward(request, response);
            	}
          
          
            
            
            	else
            	{
            
            
            // Perform validation
            	boolean isEmailValid = Validator.is_ValidEmail(email);
            	boolean isPasswordValid = Validator.is_Valid_Password(password);
            	boolean isPasswordMatch = Validator.Double_Check_Password(password, confirmPassword);

            	RequestDispatcher dispatcher;

            
            
            // Handle validation errors
            
            
            
            
            	if (!isEmailValid || !isPasswordValid || !isPasswordMatch) 
            	{

            		if (!isEmailValid) {
            			RequestDispatcher dispatcher1 = request.getRequestDispatcher("InvalidEmail.html");
            			dispatcher1.forward(request, response);
            			out.write("<br>");
            		}

            		if (!isPasswordValid) {
            			RequestDispatcher dispatcher2 = request.getRequestDispatcher("InvalidPassword.html");
            			dispatcher2.forward(request, response);
            			out.write("<br>");
            		}

            		if (!isPasswordMatch) {
            			RequestDispatcher dispatcher3 = request.getRequestDispatcher("DoubleCheckPassword.html");
            			dispatcher3.forward(request, response);
            			out.write("<br>");
            		}

                // Prompt user to try again
               
            	} 
            
            
            	else 
            	{
            		// Registration successful (DB insertion code is commented out for now)
            		//You can uncomment and use this section when ready to persist data

                
            		String query = "INSERT INTO UsersInfoInJavaTest (UserName__c, Email__c, Password__c) VALUES (?, ?, ?)";
            		PreparedStatement ps = con.prepareStatement(query);
            		ps.setString(1, username);
            		ps.setString(2, email);
            		ps.setString(3, password); // Ideally, hash the password
            		int result = ps.executeUpdate();
        

 
            		if (result > 0) {
                	
                	
                	
            			dispatcher = request.getRequestDispatcher("SuccessfullyRegistered.html");
            			dispatcher.include(request, response);
            		} 
            		else {
            			dispatcher = request.getRequestDispatcher("RegistrationFailed.html");
            			dispatcher.include(request, response);                
                	
            		}
            
            		ps.close();
                

                //out.write("Successfully Registered");
            	}

            	con.close();
        	}
            
        } 
        
        catch (Exception e)         
        {
            
        	e.printStackTrace();
            // In production, redirect to a friendly error page instead
        }
        
                 
    }
}
