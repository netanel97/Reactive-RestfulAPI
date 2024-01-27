package reactiveusersmicroservice.utils;

import reactiveusersmicroservice.boundaries.Name;
import reactiveusersmicroservice.boundaries.NewUserBoundary;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validators {

    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(Constants.FORMAT_EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(Constants.FORMAT_PASSWORD_REGEX);
        return pattern.matcher(password).matches();
    }

    public static boolean isValidDateNoFuture(String dateStr) {
        LocalDate date = DateUtils.toLocalDate(dateStr);
        if (date == null) {
            return false;
        }
        return !date.isAfter(LocalDate.now());
    }

    public static boolean isNameValid(Name name) {
        return !name.getFirst().isBlank() && !name.getLast().isBlank();
    }

    public static boolean isValidUser(NewUserBoundary user) {
        return isPasswordValid(user.getPassword()) &&
                isEmailValid(user.getEmail()) &&
                isValidDateNoFuture(user.getBirthdate()) &&
                isValidDateNoFuture(user.getRecruitdate()) &&
                isNameValid(user.getName());
    }
}
