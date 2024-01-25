package reactiveusersmicroservice.utils;

import reactiveusersmicroservice.boundaries.Name;
import reactiveusersmicroservice.boundaries.NewUserBoundary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static reactiveusersmicroservice.utils.Constants.DATE_FORMAT;

public class Validators {

        private static boolean isEmailValid(String email) {
            return email.contains("@");
        }

        private static boolean isPasswordValid(String password) {
            return password.length() >= 3;
        }

        private static boolean isValidDate(String dateStr) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT); // Adjust this to match your DATE_FORMAT
            try {
                LocalDate date = LocalDate.parse(dateStr, dateTimeFormatter);
                return !date.isAfter(LocalDate.now()); // Check that the date is not in the future
            } catch (DateTimeParseException e) {
                return false; // If parsing fails, the format is invalid
            }
        }
        public static boolean isValidUser(NewUserBoundary user)
        {
            if (!isPasswordValid(user.getPassword()))
                System.err.println("password invalid");
            else if(!isEmailValid(user.getEmail()))
                System.err.println("email invalid");
            else if(!isNameValid(user.getName()))
                System.err.println("name invalid");
            else if(!isValidDate(user.getBirthdate()))
                System.err.println("birthday invalid");
            else if(!isValidDate(user.getRecruitdate()))
                System.err.println("reqruitdate invalid");
            return isPasswordValid(user.getPassword())&&
                        isEmailValid(user.getEmail())&&
                        isValidDate(user.getBirthdate())&&
                        isValidDate(user.getRecruitdate())&&
                        isNameValid(user.getName());


        }

    private static boolean isNameValid(Name name)
    {
        return !name.getFirst().isBlank() && !name.getLast().isBlank();
    }
}
