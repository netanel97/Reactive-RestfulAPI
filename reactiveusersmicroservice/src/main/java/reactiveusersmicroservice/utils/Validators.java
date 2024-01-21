package reactiveusersmicroservice.utils;

import reactiveusersmicroservice.bounderies.UserBoundary;

public class Validators {

        public static boolean isEmailValid(String email) {
            return email.contains("@");
        }

        public static boolean isPasswordValid(String password) {
            return password.length() >= 3;
        }

        public static boolean isValidUser(UserBoundary userBoundary)
        {
            return true;
        }
}
