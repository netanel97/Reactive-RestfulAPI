package reactiveusersmicroservice.controllers;

import jakarta.annotation.PostConstruct;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactiveusersmicroservice.bounderies.Name;
import reactiveusersmicroservice.bounderies.UserBoundary;
import reactiveusersmicroservice.utils.Validators;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = {"/users"})
public class ReactiveUsersController {

    // TODO: move logic to service
    // TODO: add all methods from moodle
    private List<UserBoundary> users;

    //    @Autowired
    @PostConstruct
    public void initHardcodedUsers() {
        this.users = new ArrayList<UserBoundary>();
        this.users.add(new UserBoundary("email1", new Name("first1", "last1"), "password1",
                "birthdate1", "recruitdate1", new ArrayList<String>()));

        this.users.add(new UserBoundary("email2", new Name("first2", "last2"), "password2",
                "birthdate2", "recruitdate2", new ArrayList<String>()));
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<UserBoundary> createUser(@RequestBody UserBoundary userBoundary) {
        //TODO: move to another class
        if(!Validators.isDateValid(userBoundary.getBirthdate()))
            return Mono.error(new RuntimeException("birthdate is not valid"));
        if(!Validators.isDateValid(userBoundary.getRecruitdate()))
            return Mono.error(new RuntimeException("recruitdate is not valid"));
        if(!Validators.isPasswordValid(userBoundary.getPassword()))
            return Mono.error(new RuntimeException("password is not valid"));

        //TODO: save user to DB
        return Mono.just(userBoundary)
                .log();
    }

    @GetMapping(
            path = {"/{email}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<UserBoundary> getUser(@PathVariable("email") String email,
                                      @RequestParam(name = "password", required = true) String password) {
        for (UserBoundary user : this.users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return Mono.just(user)
                        .log();
            }
        }
        return Mono.error(new RuntimeException("could not find user by email: " + email + " and password: " + password));
//        return Mono.just(null);
    }
}
