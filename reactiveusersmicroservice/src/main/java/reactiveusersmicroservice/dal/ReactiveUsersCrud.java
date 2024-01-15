package reactiveusersmicroservice.dal;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactiveusersmicroservice.data.UserEntity;
import reactor.core.publisher.Flux;

public interface ReactiveUsersCrud extends
	ReactiveMongoRepository<UserEntity, String>{
	// TODO: add methods
	/*
	public Flux<MessageEntity> findAllByMessageLike (
			@Param("pattern") String pattern);

	 */
}
