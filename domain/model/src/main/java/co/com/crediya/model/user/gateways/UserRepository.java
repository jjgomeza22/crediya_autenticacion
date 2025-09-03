package co.com.crediya.model.user.gateways;

import co.com.crediya.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserRepository {
    Mono<String> saveUser(User user);
    Mono<User> finByEmail(String email);
    Flux<User> findUsersByEmail(List<String> emails);
}
