package examination_javaservlet;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

@WebServlet("/Questions_Validator")
public class Questions_Validator extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Correct answers for 46 questions
    String[] Answers = {
        "b", "d", "c", "b", "c", "d", "a", "d", "d", "c",
        "b", "a", "d", "b", "c", "c", "b", "d", "a", "c",
        "b", "a", "d", "a", "d", "c", "d", "c", "b", "c",
        "c", "a", "c", "b", "d", "c", "a", "b", "d", "b",
        "b", "d", "c", "d", "a", "b"
    };

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int total_marks = 0;

            // Evaluate answers
            for (int i = 1; i <= 46; i++) {
                String question = "q" + i;
                String answer = request.getParameter(question);
                if (answer != null && Answers[i - 1].equals(answer)) {
                    total_marks++;
                }
            }

            // Store marks in session
            HttpSession session = request.getSession();
            session.setAttribute("totalMarks", total_marks);

            // Get email from Login_Validator session
            String email = (String)session.getAttribute("Email__c");

            // Update marks in database
            String updateQuery = "UPDATE UsersInfoInJavaTest SET marks = ? WHERE Email__c = ?";

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaTestDB", "root", "@Nu07011528");
                 PreparedStatement pstmt = con.prepareStatement(updateQuery)) {

                pstmt.setInt(1, total_marks);
                pstmt.setString(2, email);

                int rowsUpdated = pstmt.executeUpdate();
                System.out.println("Rows Affected: " + rowsUpdated);
            }

            // Forward to results page
            response.setContentType("text/html");
            RequestDispatcher dispatcher = request.getRequestDispatcher("MarksObtained.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
