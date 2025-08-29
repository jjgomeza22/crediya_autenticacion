package co.com.crediya.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String name;
    private String lastname;
    private String email;
    private String documentId;
    private String phoneNumber;
    private Integer roleId;
    private BigDecimal baseSalary;
    private LocalDate birthDay;
    private String address;
    private String password;

}
