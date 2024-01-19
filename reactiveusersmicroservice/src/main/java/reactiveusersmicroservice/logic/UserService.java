package reactiveusersmicroservice.logic;

import io.micrometer.common.util.internal.logging.InternalLogger;
import reactiveusersmicroservice.bounderies.UserBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
	//TODO: add methods

    public Mono<UserBoundary> createUser(UserBoundary user);

    public Flux<UserBoundary> getUsersByDomain(String domain);

    public Flux<UserBoundary> getUsersByLastName(String lastName);

    public Mono<Void> deleteAll();

    public Flux<UserBoundary> getAll();

    public Mono<UserBoundary> getSpecificUserByEmailAndPassword(String email, String password);

    public Flux<UserBoundary> getUsersByCriteria(String criteria, String domain);
}
