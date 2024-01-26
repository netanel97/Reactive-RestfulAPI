package reactiveusersmicroservice.utils;


import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static reactiveusersmicroservice.utils.Constants.FORMAT_DATE;

@Component
public class DateUtils {

    /**
     * Checks if a string is in a valid date format
     *
     * @param dateStr - the string to check
     * @return true if the string is in the correct format, false otherwise
     */
    public static boolean isValidDateFormat(String dateStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT_DATE);
        try {
            LocalDate date = LocalDate.parse(dateStr, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            // If parsing fails - the format is invalid
            return false;
        }
        // Parsing succeeded - the format is valid
        return true;
    }

    /**
     * Checks if a string is in a valid date format
     *
     * @param dateStr - the string to check
     * @param format  - the format of the string
     * @return true if the string is in the correct format, false otherwise
     */
    public static boolean isValidDateFormat(String dateStr, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        try {
            LocalDate date = LocalDate.parse(dateStr, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            // If parsing fails - the format is invalid
            return false;
        }
        // Parsing succeeded - the format is valid
        return true;
    }

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
     * Converts a LocalDate object to a string
     *
     * @param date   - the date to convert
     * @param format - the format of the string
     * @return the string representation of the date
     */
    public static String toString(LocalDate date, String format) {
        try {
            if (date != null && format != null) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.format(date);
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
     * Converts a string to a LocalDate object
     *
     * @param dateString - the string to convert
     * @param format     - the format of the string
     * @return LocalDate object if the string is in the correct format, null otherwise
     */
    public static LocalDate toLocalDate(String dateString, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
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
