package reactiveusersmicroservice.dal;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactiveusersmicroservice.data.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveUsersCrud extends ReactiveMongoRepository<UserEntity, String>{

	public Flux<UserEntity> findAllByName_LastIgnoreCase (
			@Param("lastName") String lastName);

	public Flux<UserEntity> findAllByEmailEndingWith(
			@Param("domain") String domain);

	public Mono<UserEntity> findByEmailAndPassword(
			@Param("email") String email,
			@Param("password") String password);

	@Query("{ 'departments' : ?0 }")
	public Flux<UserEntity> findAllUsersByDeptId(String deptId);
}
