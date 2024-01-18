package reactiveusersmicroservice.logic;

import reactiveusersmicroservice.bounderies.UserBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
	//TODO: add methods

    public Mono<UserBoundary> createUser(UserBoundary user);

    public Flux<UserBoundary> getUsersByDomain(String domain);

    public Mono<Void> deleteAll();

    public Flux<UserBoundary> getAll();
}
