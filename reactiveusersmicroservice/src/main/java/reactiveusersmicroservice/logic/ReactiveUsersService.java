package reactiveusersmicroservice.logic;

import org.springframework.stereotype.Service;
import reactiveusersmicroservice.bounderies.UserBoundary;
import reactiveusersmicroservice.dal.ReactiveUsersCrud;
import reactiveusersmicroservice.utils.UsersConverter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactiveusersmicroservice.utils.Constants.*;

@Service
public class ReactiveUsersService implements UsersService {
    private UsersConverter usersConverter;
    private ReactiveUsersCrud reactiveUsersCrud;

    public ReactiveUsersService(ReactiveUsersCrud reactiveUsersCrud, UsersConverter usersConverter) {
        this.reactiveUsersCrud = reactiveUsersCrud;
        this.usersConverter = usersConverter;
    }


    @Override
    public Mono<UserBoundary> createUser(UserBoundary user) {
        return this.reactiveUsersCrud.existsById(user.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.just(new UserBoundary()); //TODO: need to check if need to throw exception or 200ok
                    } else {
                        return Mono.just(user)
                                .map(this.usersConverter::toEntity)
                                .flatMap(this.reactiveUsersCrud::save)
                                .map(this.usersConverter::toBoundary);
                    }
                });
    }



    private Flux<UserBoundary> getUsersByDomain(String domain){
        return this.reactiveUsersCrud
                .findAllByEmailLike("*" + DOMAIN + domain)
                .map(usersConverter::toBoundary);
    }



    private Flux<UserBoundary> getUsersByLastName(String lastName){
        return this.reactiveUsersCrud
                .findAllByName_LastIgnoreCase(lastName)
                .map(usersConverter::toBoundary);
    }


    @Override
    public Mono<UserBoundary> getSpecificUserByEmailAndPassword(String email, String password){
        return this.reactiveUsersCrud
                .findByEmailAndPassword(email, password)
                .map(usersConverter::toBoundary);
    }
    /**
     * Get all users that are older than the minimum age
     * @param miniMinimumAge defines the minimum age
     * @return Flux<UserBoundary>
     */
    private Flux<UserBoundary> getUsersByMinimumAge(String miniMinimumAge){
        return this.reactiveUsersCrud.findAll()
                .map(usersConverter::toBoundary)
                .filter(userBoundary -> usersConverter.isOlderThen(miniMinimumAge, userBoundary.getBirthdate()));
    }

    /**
     * Get users by criteria
     * @param criteria defines the criteria to search by
     * @param value defines the domain to search by
     * @return Flux<UserBoundary>
     */
    @Override
    public Flux<UserBoundary> getUsersByCriteria(String criteria, String value) {
        return switch (criteria) {
            case (CRITERIA_DOMAIN) -> this.getUsersByDomain(value);
            case (CRITERIA_LASTNAME) -> this.getUsersByLastName(value);
            case (CRITERIA_MINIMUM_AGE) -> this.getUsersByMinimumAge(value);
            default ->
                    Flux.error(new RuntimeException("Invalid criteria"));//TODO: need to check if need to throw exception or 200ok
        };
    }

    /**
     * Delete all users from the database
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> deleteAll() {
        return this.reactiveUsersCrud
                .deleteAll();
    }

    /**
     * Get all users from the database
     * @return Flux<UserBoundary>
     */
    @Override
    public Flux<UserBoundary> getAll() {
        return this.reactiveUsersCrud
                .findAll()
                .map(this.usersConverter::toBoundary);
    }
}
