package reactiveusersmicroservice.controllers;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactiveusersmicroservice.bounderies.DepartmentInvoker;
import reactiveusersmicroservice.bounderies.UserBoundary;
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
    public Mono<UserBoundary> createUser(@RequestBody UserBoundary userBoundary) {
        return this.usersService
                .createUser(userBoundary)
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

    /**
     * Delete all users from the database
     * @return void
     */
    @DeleteMapping
    public Mono<Void> deleteAll(){
        return this.usersService
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
        return this.usersService
                .getAll()
                .log();
    }


    //TODO: to check if we need to check the criteria by if(criteria.equals("byDomain"))
    @GetMapping(
            params = {"criteria", "value"},
            produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<UserBoundary> getUsersByCriteria(@RequestParam(name = "criteria", required = true) String criteria,
                                               @RequestParam(name = "value", required = true) String value) {
            return this.usersService
                    .getUsersByCriteria(criteria,value)
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
