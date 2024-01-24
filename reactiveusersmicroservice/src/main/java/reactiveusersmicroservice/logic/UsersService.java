package reactiveusersmicroservice.logic;

import reactiveusersmicroservice.boundaries.DepartmentInvoker;
import reactiveusersmicroservice.boundaries.EncryptedUserBoundary;
import reactiveusersmicroservice.boundaries.UserBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsersService {

    public Mono<EncryptedUserBoundary> createUser(UserBoundary user);

    public Mono<Void> deleteAll();

    public Flux<EncryptedUserBoundary> getAll();

    public Mono<EncryptedUserBoundary> getSpecificUserByEmailAndPassword(String email, String password);

    public Flux<EncryptedUserBoundary> getUsersByCriteria(String criteria, String value);

    public Mono<Void> bindUserDepartment(String email, DepartmentInvoker department);

    public Mono<Void> removeAllDepartmentsFromUsers();

}
