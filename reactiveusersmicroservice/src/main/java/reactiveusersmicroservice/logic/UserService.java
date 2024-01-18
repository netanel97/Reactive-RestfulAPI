package reactiveusersmicroservice.logic;

import reactiveusersmicroservice.bounderies.UserBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
	//TODO: add methods

    public Mono<UserBoundary> createUser(UserBoundary user);

    public Flux<UserBoundary> getUsersByDomain(String domain);
/*
	public Mono<User> getMessageById(String id);

	public Mono<MessageBoundary> createMessage(MessageBoundary message);

	public Flux<MessageBoundary> getAll();
	public Flux<MessageBoundary> getByPrefix(String prefix);

	public Mono<Void> deleteAll();
*/

}
