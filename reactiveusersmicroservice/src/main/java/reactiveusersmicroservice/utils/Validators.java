package reactiveusersmicroservice.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validators {

        public static boolean isEmailValid(String email) {
            return email.contains("@");
        }

        public static boolean isPasswordValid(String password) {
            return password.length() >= 3;
        }

        public static boolean isDateValid(String date) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            try {
                LocalDate.parse(date, formatter);
                return true;
            } catch (DateTimeParseException e) {
                return false;
            }
        }
}
