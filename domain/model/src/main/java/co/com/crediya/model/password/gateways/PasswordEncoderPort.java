package co.com.crediya.model.password.gateways;

import reactor.core.publisher.Mono;

public interface PasswordEncoderPort {
    Mono<String> encode(String password);
   boolean matches(String rawPassword, String encodedPassword);
}
