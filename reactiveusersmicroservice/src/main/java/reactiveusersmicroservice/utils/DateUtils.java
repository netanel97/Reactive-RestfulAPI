package reactiveusersmicroservice.utils;


import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static reactiveusersmicroservice.utils.Constants.FORMAT_DATE;

@Component
public class DateUtils {

    /**
     * Converts a LocalDate object to a string
     *
     * @param date - the date to convert
     * @return the string representation of the date
     */
    public static String toString(LocalDate date) {
        try {
            if (date != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE);
                return date.format(formatter);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Converts a string to a LocalDate object
     *
     * @param dateString - the string to convert
     * @return LocalDate object if the string is in the correct format, null otherwise
     */
    public static LocalDate toLocalDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE);
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    /**
     * Calculates the age from a birthdate
     *
     * @param birthDate - the birthdate
     * @return the age
     */
    public static int getAgeFromBirthDate(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears(); // Calculate the age
    }

    /**
     * Checks if a birthdate is older than a minimum age
     *
     * @param minimumAge - the minimum age
     * @param birthdate  - the birthdate
     * @return true if the age is greater or equal to the minimum age, false otherwise
     */
    public static boolean isAgeGreaterThanByBirthdate(String birthdate, String minimumAge) {
        try {
            int minimumAgeInt = Integer.parseInt(minimumAge); // Parse the minimum age
            return isAgeGreaterThanByBirthdate(minimumAgeInt, birthdate); // Return true if the age is greater or equal to the minimum age
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a birthdate is older than a minimum age
     *
     * @param minimumAge - the minimum age
     * @param birthdate  - the birthdate
     * @return true if the age is greater or equal to the minimum age, false otherwise
     */
    public static boolean isAgeGreaterThanByBirthdate(int minimumAge, String birthdate) {
        return getAgeFromBirthDate(toLocalDate(birthdate)) >= minimumAge;
    }

}
