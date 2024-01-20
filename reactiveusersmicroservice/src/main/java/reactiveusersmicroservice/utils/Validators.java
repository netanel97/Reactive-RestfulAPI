package reactiveusersmicroservice.utils;

import reactiveusersmicroservice.bounderies.UserBoundary;

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



        //TODO: need to add more validations
        public static boolean isValidUser(UserBoundary userBoundary)
        {
            return true;
        }
}
