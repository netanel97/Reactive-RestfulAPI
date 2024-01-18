package reactiveusersmicroservice.controllers;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactiveusersmicroservice.bounderies.UserBoundary;
import reactiveusersmicroservice.logic.UserService;
import reactor.core.publisher.Flux;
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
        return this.userService
                .getSpecificUserByEmailAndPassword(email, password)
                .log();
    }

    /**
     * Delete all users from the database
     * @return void
     */
    @DeleteMapping
    public Mono<Void> deleteAll(){
        return this.userService
                .deleteAll()
                .log();
    }

    /**
     * Get all users from the database
     * @return Flux<UserBoundary>
     */
    @GetMapping(
            produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<UserBoundary> getAllUsers (){
        return this.userService
                .getAll()
                .log();
    }


    //TODO: to check if we need to check the criteria by if(criteria.equals("byDomain"))
    @GetMapping(
            path = {"/byDomain"},
            produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<UserBoundary> getUsersByDomain(@RequestParam(name = "criteria", required = true) String criteria,
                                               @RequestParam(name = "value", required = true) String domain) {
            return this.userService
                    .getUsersByDomain(domain)
                    .log();
        }

        @GetMapping(
                path = {"/byLastname"},
                produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
        public Flux<UserBoundary> getUsersByLastName(@RequestParam(name = "criteria", required = true) String criteria,
                                                     @RequestParam(name = "value", required = true) String lastName) {
                return this.userService
                        .getUsersByLastName(lastName)
                        .log();
        }



    }
