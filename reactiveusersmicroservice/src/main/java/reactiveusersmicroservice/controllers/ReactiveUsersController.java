package reactiveusersmicroservice.controllers;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactiveusersmicroservice.boundaries.DepartmentInvoker;
import reactiveusersmicroservice.boundaries.UserBoundary;
import reactiveusersmicroservice.boundaries.NewUserBoundary;
import reactiveusersmicroservice.logic.UsersService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/users"})
public class ReactiveUsersController {

    private UsersService usersService;

    public ReactiveUsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<UserBoundary> createUser(@RequestBody NewUserBoundary newUserBoundary) {
        return this.usersService
                .createUser(newUserBoundary)
                .log();
    }

    @GetMapping(
            path = {"/{email}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<UserBoundary> getUser(@PathVariable("email") String email,
                                      @RequestParam(name = "password", required = true) String password) {
        return this.usersService
                .getSpecificUserByEmailAndPassword(email, password)
                .log();
    }

    @GetMapping(
            produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<UserBoundary> getUsers(@RequestParam(name = "criteria", required = false) String criteria,
                                       @RequestParam(name = "value", required = false) String value) {
            return this.usersService
                    .getUsers(criteria,value)
                    .log();
        }

    @DeleteMapping
    public Mono<Void> deleteAll(){
        return this.usersService
                .deleteAll()
                .log();
    }

    @PutMapping(
            path = {"/{email}/department"},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Void> bindUserDepartment(@PathVariable("email") String email,
                                           @RequestBody DepartmentInvoker department) {
        return this.usersService
                .bindUserDepartment(email, department)
                .log();
    }

}
