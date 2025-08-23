package co.com.crediya.r2dbc.entity;

import co.com.crediya.model.role.RoleName;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "rol")
public class RoleEntity {
    @Id
    @Column("id_role")
    private Integer roleId;
    @Column("nombre")
    private RoleName name;
    @Column("descripcion")
    private String description;
}
