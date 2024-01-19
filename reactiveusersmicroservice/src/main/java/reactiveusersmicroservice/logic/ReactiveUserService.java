package reactiveusersmicroservice.logic;

import org.springframework.stereotype.Service;
import reactiveusersmicroservice.bounderies.UserBoundary;
import reactiveusersmicroservice.dal.ReactiveUsersCrud;
import reactiveusersmicroservice.utils.UserConverter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactiveusersmicroservice.utils.Constants.*;

@Service
public class ReactiveUserService implements UserService {
    private UserConverter userConverter;
    private ReactiveUsersCrud reactiveUsersCrud;

    public ReactiveUserService(ReactiveUsersCrud reactiveUsersCrud, UserConverter userConverter) {
        this.reactiveUsersCrud = reactiveUsersCrud;
        this.userConverter = userConverter;
    }


    @Override
    public Mono<UserBoundary> createUser(UserBoundary user) {
        return this.reactiveUsersCrud.existsById(user.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        System.err.println("User already exists");
                        return Mono.just(new UserBoundary()); //TODO: need to check if need to throw exception or 200ok
                    } else {
                        return Mono.just(user)
                                .map(this.userConverter::toEntity)
                                .flatMap(this.reactiveUsersCrud::save)
                                .map(this.userConverter::toBoundary);
                    }
                });
    }


    @Override
    public Flux<UserBoundary> getUsersByDomain(String domain){
        return this.reactiveUsersCrud
                .findAllByEmailLike("*" + DOMAIN + domain)
                .map(userConverter::toBoundary);
    }


    @Override
    public Flux<UserBoundary> getUsersByLastName(String lastName){
        return this.reactiveUsersCrud
                .findAllByName_LastIgnoreCase(lastName)
                .map(userConverter::toBoundary);
    }


    @Override
    public Mono<UserBoundary> getSpecificUserByEmailAndPassword(String email, String password){
        return this.reactiveUsersCrud
                .findByEmailAndPassword(email, password)
                .map(userConverter::toBoundary);
    }

    @Override
    public Flux<UserBoundary> getUsersByCriteria(String criteria, String domain) {
        return switch (criteria) {
            case (DOMAIN_CRITERIA) -> this.getUsersByDomain(domain);
            case (DOMAIN_BY_LASTNAME) -> this.getUsersByLastName(domain);
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
                .map(this.userConverter::toBoundary);
    }
}
