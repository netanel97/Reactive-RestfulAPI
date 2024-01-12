package reactiveusersmicroservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(path = {"/users"})
public class ReactiveUsersController {


    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<UserBoundary> createUser(UserBoundary userBoundary) {
        return Mono.just(userBoundary)
                .log();
    }

}
