package reactiveusersmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveUsersMicroservice {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveUsersMicroservice.class, args);
    }

}


/*
NEED TO FIX USER BOUNDARIES TO MATCH CHANGES MADE WITH ADDING DEPARTMENTS
test
POST OF NEW USER DOES NOT CHECK DATES
POST OF NEW USER CAN BE REGISTERED WITH NO PASSWORD

SEARCH OF CRITERIA THROWS EXEPTION WHEN CRITERIA IS INVALID AND THE CRITERIA BOX IS NOT CASE SENSITIVE (NOT NECCERALY A PROBLEM)

ON SEARCH BY BIRTHDATE BECAUSE SOME OF THE USERS DIDNT HAVE VALID BIRTHDATES IT CRASHES NEED TO FIX CREATE NEW USER

 */