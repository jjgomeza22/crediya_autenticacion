package co.com.crediya.usecase.exception;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {
    public static <T> Mono<T> emailAlreadyExist(String email) {
        return Mono.error(new DuplicateEmailException(email));
    }

    public static <T> Mono<T> invalidCredentials() {
        return Mono.error(new InvalidCredentialsException("Incorrect email or password"));
    }
}
