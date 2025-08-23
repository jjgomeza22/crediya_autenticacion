package co.com.crediya.api.validator;

import co.com.crediya.api.dto.SaveUserDto;
import co.com.crediya.api.exception.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {
    public static UnaryOperator<Mono<SaveUserDto>> validate() {
        return mono -> mono
                .filter(hasName())
                .switchIfEmpty(ApplicationExceptions.missingName())
                .filter(hasLastName())
                .switchIfEmpty(ApplicationExceptions.missingLastName())
                .filter(hasEmail())
                .switchIfEmpty(ApplicationExceptions.missingEmail())
                .filter(hasSalary())
                .switchIfEmpty(ApplicationExceptions.missingSalary());
    }

    private static Predicate<SaveUserDto> hasName() {
        return dto -> Objects.nonNull(dto.name()) && !dto.name().isEmpty();
    }

    private static Predicate<SaveUserDto> hasLastName() {
        return dto -> Objects.nonNull(dto.lastname()) && !dto.lastname().isEmpty();
    }

    private static Predicate<SaveUserDto> hasEmail() {
        return dto -> Objects.nonNull(dto.email()) && !dto.email().isEmpty();
    }

    private static Predicate<SaveUserDto> hasSalary() {
        return dto -> Objects.nonNull(dto.baseSalary());
    }
}

