package reactiveusersmicroservice.controllers;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactiveusersmicroservice.bounderies.UserBoundary;
import reactiveusersmicroservice.logic.UserService;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping(path = {"/users"})
public class ReactiveUsersController {

    private UserService userService;

    public ReactiveUsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<UserBoundary> createUser(@RequestBody UserBoundary userBoundary) {
        return this.userService
                .createUser(userBoundary)
                .log();
    }

    @GetMapping(
            path = {"/{email}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<UserBoundary> getUser(@PathVariable("email") String email,
                                      @RequestParam(name = "password", required = true) String password) {
//        for (UserBoundary user : this.users) {
//            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
//                return Mono.just(user)
//                        .log();
//            }
//        }
        return Mono.error(new RuntimeException("could not find user by email: " + email + " and password: " + password));
//        return Mono.just(null);
    }
}
