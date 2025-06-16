package examination_javaservlet;

import java.util.regex.*;

public class Validator {

    // Validates if the name contains only letters and is at least 2 characters long
    static boolean is_ValidName(String name) {
        return name != null && name.matches("^[A-Za-z]{2,}$");
    }

    // Validates the email using a regex pattern
    static boolean is_ValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    // Checks if password and confirm_password are equal (already correctly implemented)
    static boolean Double_Check_Password(String password, String confirm_password) {
        return password != null && password.equals(confirm_password);
    }

    // Validates password strength: at least 8 characters, contains upper, lower, digit, and special char
    static boolean is_Valid_Password(String password) {
        if (password == null) return false;
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(passwordRegex);
    }
}
