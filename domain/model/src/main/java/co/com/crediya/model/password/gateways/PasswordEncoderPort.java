package co.com.crediya.model.password.gateways;

import reactor.core.publisher.Mono;

public interface PasswordEncoderPort {
    Mono<String> encode(String password);
    Mono<Boolean> matches(String rawPassword, String encodedPassword);
}
