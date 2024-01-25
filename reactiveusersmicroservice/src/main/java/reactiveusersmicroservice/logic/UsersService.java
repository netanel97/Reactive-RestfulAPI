package reactiveusersmicroservice.logic;

import reactiveusersmicroservice.boundaries.DepartmentInvoker;
import reactiveusersmicroservice.boundaries.UserBoundary;
import reactiveusersmicroservice.boundaries.NewUserBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsersService {

    public Mono<UserBoundary> createUser(NewUserBoundary user);

    public Mono<UserBoundary> getSpecificUserByEmailAndPassword(String email, String password);

    Flux<UserBoundary> getUsers(String criteria, String value);

    public Mono<Void> deleteAll();

    public Mono<Void> bindUserDepartment(String email, DepartmentInvoker department);

    public Mono<Void> removeAllDepartmentsFromUsers();

}
