package reactiveusersmicroservice.logic;

import org.springframework.stereotype.Service;
import reactiveusersmicroservice.bounderies.DepartmentInvoker;
import reactiveusersmicroservice.bounderies.UserBoundary;
import reactiveusersmicroservice.dal.ReactiveDepartmentsCrud;
import reactiveusersmicroservice.dal.ReactiveUsersCrud;
import reactiveusersmicroservice.utils.UsersConverter;
import reactiveusersmicroservice.utils.Validators;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static reactiveusersmicroservice.utils.Constants.*;


@Service
public class ReactiveUsersService implements UsersService {
    private UsersConverter usersConverter;
    private ReactiveUsersCrud reactiveUsersCrud;
    private ReactiveDepartmentsCrud reactiveDepartmentsCrud;

    public ReactiveUsersService(ReactiveUsersCrud reactiveUsersCrud, UsersConverter usersConverter, ReactiveDepartmentsCrud reactiveDepartmentsCrud) {
        this.reactiveUsersCrud = reactiveUsersCrud;
        this.usersConverter = usersConverter;
        this.reactiveDepartmentsCrud = reactiveDepartmentsCrud;
    }


    @Override
    public Mono<UserBoundary> createUser(UserBoundary user) {
        return this.reactiveUsersCrud.existsById(user.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.empty(); //TODO: need to check if need to throw exception or 200ok
                    } else {
                        if(Validators.isValidUser(user))
                        return Mono.just(user)
                                .map(this.usersConverter::toEntity)
                                .flatMap(this.reactiveUsersCrud::save)
                                .map(this.usersConverter::toBoundary);
                        else
                            return Mono.empty();
                    }
                });
    }


    private Flux<UserBoundary> getUsersByDomain(String domain) {
        return this.reactiveUsersCrud
                .findAllByEmailLike("*" + DOMAIN + domain)
                .map(usersConverter::toBoundary);
    }


    private Flux<UserBoundary> getUsersByLastName(String lastName) {
        return this.reactiveUsersCrud
                .findAllByName_LastIgnoreCase(lastName)
                .map(usersConverter::toBoundary);
    }


    @Override
    public Mono<UserBoundary> getSpecificUserByEmailAndPassword(String email, String password) {
        return this.reactiveUsersCrud
                .findByEmailAndPassword(email, password)
                .map(usersConverter::toBoundary);
    }

    /**
     * Get all users that are older than the minimum age
     *
     * @param miniMinimumAge defines the minimum age
     * @return Flux<UserBoundary>
     */
    private Flux<UserBoundary> getUsersByMinimumAge(String miniMinimumAge) {
        return this.reactiveUsersCrud.findAll()
                .map(usersConverter::toBoundary)
                .filter(userBoundary -> usersConverter.isOlderThen(miniMinimumAge, userBoundary.getBirthdate()));
    }

    /**
     * Get users by criteria
     *
     * @param criteria defines the criteria to search by
     * @param value    defines the domain to search by
     * @return Flux<UserBoundary>
     */
    @Override
    public Flux<UserBoundary> getUsersByCriteria(String criteria, String value) {
        return switch (criteria) {
            case (CRITERIA_DOMAIN) -> this.getUsersByDomain(value);
            case (CRITERIA_LASTNAME) -> this.getUsersByLastName(value);
            case (CRITERIA_MINIMUM_AGE) -> this.getUsersByMinimumAge(value);
            case (CRITERIA_DEPARTMENT) -> this.getUsersByDepartmentId(value);
            default ->
                    Flux.error(new RuntimeException("Invalid criteria"));//TODO: need to check if need to throw exception or 200ok
        };
    }

    /**
     * Get users by department id
     *
     * @param value defines the department id to search by
     * @return Flux<UserBoundary>
     */

    private Flux<UserBoundary> getUsersByDepartmentId(String value) {
        return this.reactiveUsersCrud.findAllUsersByDeptId(value)
                .map(usersConverter::toBoundary);
    }

    @Override
    public Mono<Void> bindUserDepartment(String email, DepartmentInvoker department) {
        return this.reactiveUsersCrud.existsById(email)
                .flatMap(exists -> {
                    System.err.println(exists);
                    if (!exists) {
                        return Mono.empty(); // User does not exist
                    }
                    return reactiveDepartmentsCrud.existsById(department.getDepartment().getDepId())
                            .flatMap(deptExists -> {
                                if (!deptExists) {
                                    return Mono.empty(); // Department does not exist
                                }
                                return this.reactiveUsersCrud.findById(email) // finding user by email
                                        .flatMap(user -> { // User Entity
                                            String depId = department.getDepartment().getDepId();
                                            if (!user.getDepartments().contains(depId)) {
                                                user.getDepartments().add(depId); // Add department if not already present
                                                return this.reactiveUsersCrud.save(user).then(); // Save and convert to Mono<Void>
                                            }

                                            return Mono.empty(); // Department already present, no action needed
                                        });
                            });
                });
    }


    /**
     * Delete all users from the database
     *
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> deleteAll() {
        return this.reactiveUsersCrud
                .deleteAll();
    }

    /**
     * Get all users from the database
     *
     * @return Flux<UserBoundary>
     */
    @Override
    public Flux<UserBoundary> getAll() {
        return this.reactiveUsersCrud
                .findAll()
                .map(this.usersConverter::toBoundary);
    }

    @Override
    public Mono<Void> removeAllDepartmentsFromUsers() {
        return this.reactiveUsersCrud
                .findAll()
                .flatMap(user -> {
                    user.getDepartments().clear(); // Clearing the departments set
                    return this.reactiveUsersCrud.save(user); // Saving the user back to the database
                }).then();
    }

}