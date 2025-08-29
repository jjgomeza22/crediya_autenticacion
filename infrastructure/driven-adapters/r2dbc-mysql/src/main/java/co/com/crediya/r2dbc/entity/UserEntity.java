package co.com.crediya.r2dbc.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "usuario")
public class UserEntity {
    @Id
    @Column("id_usuario")
    private Integer id_usuario;
    @Column("nombre")
    private String name;
    @Column("apellido")
    private String lastname;
    @Column("email")
    private String email;
    @Column("documento_identidad")
    private String documentId;
    @Column("telefono")
    private String phoneNumber;
    @Column("id_rol")
    private Integer roleId;
    @Column("salario_base")
    private BigDecimal baseSalary;
    @Column("fecha_nacimiento")
    private LocalDate birthDay;
    @Column("direccion")
    private String address;
    @Column("contrasena")
    private String password;
}
