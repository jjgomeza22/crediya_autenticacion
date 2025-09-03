package co.com.crediya.api.dto;

import java.math.BigDecimal;

public record UserByEmailDto(
        String name,
        String lastname,
        String email,
        BigDecimal baseSalary
) {
}
