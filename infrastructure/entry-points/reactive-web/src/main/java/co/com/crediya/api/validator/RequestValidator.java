package co.com.crediya.api.validator;

import co.com.crediya.api.dto.SaveUserDto;
import co.com.crediya.api.exception.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class RequestValidator {
    private static final BigDecimal MAX_SALARY_ALLOW = new BigDecimal(15000000);
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static UnaryOperator<Mono<SaveUserDto>> validate() {
        return mono -> mono
                .filter(hasName())
                .switchIfEmpty(ApplicationExceptions.missingName())
                .filter(hasLastName())
                .switchIfEmpty(ApplicationExceptions.missingLastName())
                .filter(hasEmail())
                .switchIfEmpty(ApplicationExceptions.missingEmail())
                .filter(hasValidEmail())
                .switchIfEmpty(ApplicationExceptions.invalidEmailFormat())
                .filter(hasSalary())
                .switchIfEmpty(ApplicationExceptions.missingSalary())
                .filter(hasValidSalary())
                .switchIfEmpty(ApplicationExceptions.invalidSalaryAmount());
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

    private static Predicate<SaveUserDto> hasValidSalary() {
        return dto -> dto.baseSalary().compareTo(BigDecimal.ZERO) >= 0 && dto.baseSalary().compareTo(MAX_SALARY_ALLOW) <= 0;
    }

    private static Predicate<SaveUserDto> hasValidEmail() {
        return dto -> EMAIL_PATTERN.matcher(dto.email()).matches();
    }
}

