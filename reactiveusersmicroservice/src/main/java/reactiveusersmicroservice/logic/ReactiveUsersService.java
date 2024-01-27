package reactiveusersmicroservice.logic;

import org.springframework.stereotype.Service;
import reactiveusersmicroservice.boundaries.DepartmentInvoker;
import reactiveusersmicroservice.boundaries.UserBoundary;
import reactiveusersmicroservice.boundaries.NewUserBoundary;
import reactiveusersmicroservice.dal.ReactiveDepartmentsCrud;
import reactiveusersmicroservice.dal.ReactiveUsersCrud;
import reactiveusersmicroservice.converters.UsersConverter;
import reactiveusersmicroservice.utils.DateUtils;
import reactiveusersmicroservice.utils.Validators;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactiveusersmicroservice.utils.Constants.*;


@Service
public class ReactiveUsersService implements UsersService {
    private final UsersConverter usersConverter;
    private final ReactiveUsersCrud reactiveUsersCrud;
    private final ReactiveDepartmentsCrud reactiveDepartmentsCrud;

    public ReactiveUsersService(ReactiveUsersCrud reactiveUsersCrud, UsersConverter usersConverter, ReactiveDepartmentsCrud reactiveDepartmentsCrud) {
        this.reactiveUsersCrud = reactiveUsersCrud;
        this.usersConverter = usersConverter;
        this.reactiveDepartmentsCrud = reactiveDepartmentsCrud;
    }

    /**
     * Create a new user in the database
     *
     * @param user defines the user to create
     * @return Mono<NewUserBoundary>
     */
    @Override
    public Mono<UserBoundary> createUser(NewUserBoundary user) {
        return this.reactiveUsersCrud.existsById(user.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.empty();
                    } else {
                        if (Validators.isValidUser(user))
                            return Mono.just(user)
                                    .map(this.usersConverter::toEntity)
                                    .flatMap(this.reactiveUsersCrud::save)
                                    .map(this.usersConverter::toBoundary);
                        else
                            return Mono.empty();
                    }
                });
    }

    /**
     * Get a specific user from the database by email
     *
     * @param email    defines the email to search by
     * @param password defines the password to check match
     * @return Mono<NewUserBoundary>
     */
    @Override
    public Mono<UserBoundary> getSpecificUserByEmailAndPassword(String email, String password) {
        return this.reactiveUsersCrud
                .findByEmailAndPassword(email, password)
                .map(usersConverter::toBoundary);
    }

    /**
     * Get users from the database
     *
     * @param criteria defines the criteria to search by
     *                 (domain, last name, minimum age, department)
     *                 if null, return all users
     *                 if not null, return users by criteria
     * @param value    defines the value to search by
     *                 if criteria is null, value is ignored
     *                 return Flux<NewUserBoundary>
     */
    @Override
    public Flux<UserBoundary> getUsers(String criteria, String value) {
        if (criteria == null) {
            if (value != null)
                return Flux.empty();
            else
                return this.getAll();
        } else if (value == null)
            return Flux.empty();
        else
            return getUsersByCriteria(criteria, value);
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
     * bind a user to a department
     *
     * @param email      defines the email of the user to bind
     * @param department defines the department to bind to
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> bindUserDepartment(String email, DepartmentInvoker department) {
        return this.reactiveUsersCrud.existsById(email)
                .flatMap(exists -> {
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
     * Remove all departments from all users
     *
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> removeAllDepartmentsFromUsers() {
        return this.reactiveUsersCrud
                .findAll()
                .flatMap(user -> {
                    user.getDepartments().clear(); // Clearing the departments set
                    return this.reactiveUsersCrud.save(user); // Saving the user back to the database
                }).then();
    }

    /**
     * Get all users from the database
     *
     * @return Flux<NewUserBoundary>
     */
    public Flux<UserBoundary> getAll() {
        return this.reactiveUsersCrud
                .findAll()
                .map(this.usersConverter::toBoundary);
    }

    /**
     * Get users by criteria
     *
     * @param criteria defines the criteria to search by
     * @param value    defines the domain to search by
     * @return Flux<NewUserBoundary>
     */
    public Flux<UserBoundary> getUsersByCriteria(String criteria, String value) {
        return switch (criteria) {
            case (CRITERIA_DOMAIN) -> this.getUsersByDomain(value);
            case (CRITERIA_LASTNAME) -> this.getUsersByLastName(value);
            case (CRITERIA_MINIMUM_AGE) -> this.getUsersByMinimumAge(value);
            case (CRITERIA_DEPARTMENT) -> this.getUsersByDepartmentId(value);
            default -> Flux.empty();
        };
    }

    /**
     * Get users by domain
     *
     * @param domain defines the domain to search by
     * @return Flux<NewUserBoundary>
     */
    private Flux<UserBoundary> getUsersByDomain(String domain) {
        return this.reactiveUsersCrud
                .findAllByEmailEndingWith("@".concat(domain))
                .map(usersConverter::toBoundary);
    }

    /**
     * Get users by last name
     *
     * @param lastName defines the last name to search by
     * @return Flux<NewUserBoundary>
     */
    private Flux<UserBoundary> getUsersByLastName(String lastName) {
        return this.reactiveUsersCrud
                .findAllByName_LastIgnoreCase(lastName)
                .map(usersConverter::toBoundary);
    }

    /**
     * Get all users that are older than the minimum age
     *
     * @param miniMinimumAge defines the minimum age
     * @return Flux<NewUserBoundary>
     */
    private Flux<UserBoundary> getUsersByMinimumAge(String miniMinimumAge) {
        return this.reactiveUsersCrud.findAll()
                .map(usersConverter::toBoundary)
                .filter(userBoundary -> DateUtils.isAgeGreaterThanByBirthdate(userBoundary.getBirthdate(), miniMinimumAge));
    }

    /**
     * Get users by department id
     *
     * @param value defines the department id to search by
     * @return Flux<NewUserBoundary>
     */
    private Flux<UserBoundary> getUsersByDepartmentId(String value) {
        return this.reactiveUsersCrud.findAllUsersByDeptId(value)
                .map(usersConverter::toBoundary);
    }


}