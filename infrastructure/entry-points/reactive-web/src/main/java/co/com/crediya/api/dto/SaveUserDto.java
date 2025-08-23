package co.com.crediya.api.dto;

import java.math.BigDecimal;

public record SaveUserDto(
        String name,
        String lastname,
        String email,
        String documentId,
        String phoneNumber,
        Integer roleId,
        BigDecimal baseSalary
) {
}
