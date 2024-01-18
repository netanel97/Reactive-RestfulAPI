package reactiveusersmicroservice.logic;

import org.springframework.stereotype.Service;
import reactiveusersmicroservice.bounderies.UserBoundary;
import reactiveusersmicroservice.dal.ReactiveUsersCrud;
import reactiveusersmicroservice.utils.Converter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveUserService implements UserService {
    private Converter converter;
    private final static String DOMAIN = "@";
    private ReactiveUsersCrud reactiveUsersCrud;

    public ReactiveUserService(ReactiveUsersCrud reactiveUsersCrud,Converter converter) {
        this.reactiveUsersCrud = reactiveUsersCrud;
        this.converter = converter;
    }


    @Override
    public Mono<UserBoundary> createUser(UserBoundary user) {
        System.err.println("user: " + user);
        return Mono.just(user) // beginning reactively
                .map(this.converter::toEntity)
                .flatMap(this.reactiveUsersCrud::save)// make sure the returned Mono integrates to the reactive flow
                .map(this.converter::toBoundary);
    }


    @Override
    public Flux<UserBoundary> getUsersByDomain(String domain){
            return this.reactiveUsersCrud
                    .findAllByEmailLike("*" + DOMAIN + domain)
                    .map(converter::toBoundary);
    }


    @Override
    public Flux<UserBoundary> getUsersByLastName(String lastName){
        return this.reactiveUsersCrud
                .findAllByName_Last(lastName)
                .map(converter::toBoundary);
    }


    @Override
    public Mono<UserBoundary> getSpecificUserByEmailAndPassword(String email, String password){
        return this.reactiveUsersCrud
                .findByEmailAndPassword(email, password)
                .map(converter::toBoundary);
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
                .map(this.converter::toBoundary);
    }
}
