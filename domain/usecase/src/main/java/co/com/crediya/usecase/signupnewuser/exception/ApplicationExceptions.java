package co.com.crediya.usecase.signupnewuser.exception;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {
    public static <T> Mono<T> emailAlreadyExist(String email) {
        return Mono.error(new DuplicateEmailException(email));
    }
}
