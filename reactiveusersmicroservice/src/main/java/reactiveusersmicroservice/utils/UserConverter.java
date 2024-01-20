package reactiveusersmicroservice.utils;


import io.netty.handler.codec.DateFormatter;
import org.springframework.stereotype.Component;
import reactiveusersmicroservice.bounderies.UserBoundary;
import reactiveusersmicroservice.data.UserEntity;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static reactiveusersmicroservice.utils.Constants.DATE_FORMAT;

@Component
public class UserConverter {

    public UserEntity toEntity(UserBoundary userBoundary) {
        UserEntity entity = new UserEntity();
        entity.setEmail(userBoundary.getEmail());
        entity.setName(userBoundary.getName());
        entity.setPassword(userBoundary.getPassword());
        entity.setBirthdate(userBoundary.getBirthdate());
        entity.setRecruitdate(userBoundary.getRecruitdate());
        entity.setRoles(userBoundary.getRoles());
        return entity;

    }

    public UserBoundary toBoundary(UserEntity userEntity) {
        UserBoundary boundary = new UserBoundary();
        boundary.setEmail(userEntity.getEmail());
        boundary.setPassword("********");
        boundary.setName(userEntity.getName());
        boundary.setBirthdate(userEntity.getBirthdate());
        boundary.setRecruitdate(userEntity.getRecruitdate());
        boundary.setRoles(userEntity.getRoles());
        return boundary;
    }

    public boolean isOlderThen(String minimumAge, String birthdate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT); // Pattern for the date format
        System.err.println("birthdate: " + birthdate + " minimumAge: " + minimumAge);
        System.err.println("dateTimeFormatter: " + dateTimeFormatter);
        LocalDate birthdateDate = LocalDate.parse(birthdate, dateTimeFormatter); // Parse the date
        int minimumAgeInt = Integer.parseInt(minimumAge); // Parse the minimum age
        int ageByYears = Period.between(birthdateDate, LocalDate.now()).getYears(); // Calculate the age
        System.err.println("ageByYears: " + ageByYears + " minimumAgeInt: " + minimumAgeInt);
        return ageByYears >= minimumAgeInt; // Return true if the age is greater or equal to the minimum age
    }
}
