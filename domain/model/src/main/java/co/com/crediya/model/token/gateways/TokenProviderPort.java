package co.com.crediya.model.token.gateways;

import co.com.crediya.model.user.User;
import reactor.core.publisher.Mono;

public interface TokenProviderPort {
    Mono<String> generateToken(User user);
}
