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

	/* TODO: This is finding by domain like instead of the exact domain match.
	    	 For example:
	    	 * At the DB we have the following emails: (1) c@xemail.com, (2) c@xemail.com1.
	    	 * Test case: Sending the value domain = "xemail.com"
	    	 * Expected result: Only the first email (1) should be returned.
	    	 * Actual result: Both emails (1) and (2) are returned.
	    	 Need to be fixed, I tried and failed.
    */
	public Flux<UserEntity> findAllByEmailLike (
			@Param("pattern") String pattern);

	public Mono<UserEntity> findByEmailAndPassword(
			@Param("email") String email,
			@Param("password") String password);

	@Query("{ 'departments' : ?0 }")
	Flux<UserEntity> findAllUsersByDeptId(String deptId);

}
