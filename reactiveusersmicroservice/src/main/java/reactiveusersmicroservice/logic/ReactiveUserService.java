package reactiveusersmicroservice.logic;

import org.springframework.stereotype.Service;
import reactiveusersmicroservice.bounderies.UserBoundary;
import reactiveusersmicroservice.dal.ReactiveUsersCrud;
import reactiveusersmicroservice.utils.Converter;
import reactor.core.publisher.Mono;

@Service
public class ReactiveUserService implements UserService {
    private Converter converter;
    private ReactiveUsersCrud reactiveUsersCrud;

    public ReactiveUserService(ReactiveUsersCrud reactiveUsersCrud,Converter converter) {
        this.reactiveUsersCrud = reactiveUsersCrud;
        this.converter = converter;

    }
    @Override
    public Mono<UserBoundary> createUser(UserBoundary user) {
        System.err.println("user: " + user);
        return Mono.just(user) // beginning reactively
                .map(this.converter::toEntity)
                .flatMap(this.reactiveUsersCrud::save)// make sure the returned Mono integrates to the reactive flow
                .map(this.converter::toBoundary);
    }

    // TODO: add methods
	/*
	private ReactiveMessageCrud messageCrud;
	
	public ReactiveUserService(ReactiveMessageCrud messageCrud) {
		this.messageCrud = messageCrud;
	}
	
	@Override
	public Mono<MessageBoundary> getMessageById(String id) {
		return this.messageCrud
			.findById(id)
			.map(MessageBoundary::new);
	}

	@Override
	public Mono<MessageBoundary> createMessage(MessageBoundary message) {
		// TODO reset id of message
		return Mono.just(message) // beginning reactively
			.map(MessageBoundary::toEntity)
			.flatMap(this.messageCrud::save)// make sure the returned Mono integrates to the reactive flow
			.map(MessageBoundary::new);
	}

	@Override
	public Flux<MessageBoundary> getAll() {
		return this.messageCrud
			.findAll()
			.map(MessageBoundary::new);
	}

	@Override
	public Flux<MessageBoundary> getByPrefix(String prefix) {
		return this.messageCrud
			.findAllByMessageLike(prefix + "*")
			.map(MessageBoundary::new);
	}

	@Override
	public Mono<Void> deleteAll() {
		return this.messageCrud
			.deleteAll();
	}
*/
}
