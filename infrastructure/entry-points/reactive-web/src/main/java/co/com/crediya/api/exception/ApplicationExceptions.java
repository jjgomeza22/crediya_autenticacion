package co.com.crediya.api.exception;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {
    public static <T> Mono<T> missingName() {
        return Mono.error(new InvalidInputException("Name is required"));
    }

    public static <T> Mono<T> missingLastName() {
        return Mono.error(new InvalidInputException("LastName is required"));
    }

    public static <T> Mono<T> missingEmail() {
        return Mono.error(new InvalidInputException("Email is required"));
    }

    public static <T> Mono<T> missingSalary() {
        return Mono.error(new InvalidInputException("Salary is required"));
    }
}
