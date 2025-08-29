package co.com.crediya.api.dto;

import co.com.crediya.model.role.RoleName;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaveUserDto(
        String name,
        String lastname,
        String email,
        String documentId,
        String phoneNumber,
        RoleName role,
        BigDecimal baseSalary,
        LocalDate birthDay,
        String address,
        String password
) {
}
