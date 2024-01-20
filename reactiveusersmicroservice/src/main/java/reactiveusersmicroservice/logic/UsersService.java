package reactiveusersmicroservice.logic;

import io.micrometer.common.util.internal.logging.InternalLogger;
import reactiveusersmicroservice.bounderies.DepartmentInvoker;
import reactiveusersmicroservice.bounderies.UserBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsersService {
	//TODO: add methods

    public Mono<UserBoundary> createUser(UserBoundary user);

    public Mono<Void> deleteAll();

    public Flux<UserBoundary> getAll();

    public Mono<UserBoundary> getSpecificUserByEmailAndPassword(String email, String password);

    public Flux<UserBoundary> getUsersByCriteria(String criteria, String value);

    public Mono<Void> bindUserDepartment(String email, DepartmentInvoker department);
}
