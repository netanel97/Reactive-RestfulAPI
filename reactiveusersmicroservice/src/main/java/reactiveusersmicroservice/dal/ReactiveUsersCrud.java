package reactiveusersmicroservice.dal;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactiveusersmicroservice.data.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface ReactiveUsersCrud extends ReactiveMongoRepository<UserEntity, String>{

	public Flux<UserEntity> findAllByName_LastIgnoreCase (
			@Param("lastName") String lastName);


	//TODO: the service: (ByDomain)
	// public Flux<UserBoundary> getUsersByDomain(String domain) {
	//		return this.reactiveUsersCrud
	//			.findAllByEmailLike("*" +"@" + domain)
	//			.map(converter::toBoundary);
	//	}
	public Flux<UserEntity> findAllByEmailLike (
			@Param("pattern") String pattern);


	public Mono<UserEntity> findByEmailAndPassword(
			@Param("email") String email,
			@Param("password") String password);

	@Query("{ 'departments' : ?0 }")
	Flux<UserEntity> findAllUsersByDeptId(String deptId);

}
