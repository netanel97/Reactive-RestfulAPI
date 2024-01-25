package reactiveusersmicroservice.utils;


import org.springframework.stereotype.Component;
import reactiveusersmicroservice.boundaries.UserBoundary;
import reactiveusersmicroservice.boundaries.NewUserBoundary;
import reactiveusersmicroservice.data.UserEntity;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static reactiveusersmicroservice.utils.Constants.DATE_FORMAT;

@Component
public class UsersConverter {

    public UserEntity toEntity(NewUserBoundary newUserBoundary) {
        UserEntity entity = new UserEntity();
        entity.setEmail(newUserBoundary.getEmail());
        entity.setName(newUserBoundary.getName());
        entity.setPassword(newUserBoundary.getPassword());
        entity.setBirthdate(newUserBoundary.getBirthdate());
        entity.setRecruitdate(newUserBoundary.getRecruitdate());
        entity.setRoles(newUserBoundary.getRoles());
        return entity;

    }

    public UserBoundary toBoundary(UserEntity userEntity) {
        UserBoundary boundary = new UserBoundary();
        boundary.setEmail(userEntity.getEmail());
        boundary.setName(userEntity.getName());
        boundary.setBirthdate(userEntity.getBirthdate());
        boundary.setRecruitdate(userEntity.getRecruitdate());
        boundary.setRoles(userEntity.getRoles());
        return boundary;
    }

    public boolean isOlderThen(String minimumAge, String birthdate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT); // Pattern for the date format
        LocalDate birthdateDate = LocalDate.parse(birthdate, dateTimeFormatter); // Parse the date
        int minimumAgeInt = Integer.parseInt(minimumAge); // Parse the minimum age
        int ageByYears = Period.between(birthdateDate, LocalDate.now()).getYears(); // Calculate the age
        return ageByYears >= minimumAgeInt; // Return true if the age is greater or equal to the minimum age
    }
}
