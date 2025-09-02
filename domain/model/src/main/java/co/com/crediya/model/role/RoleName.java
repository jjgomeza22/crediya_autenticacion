package co.com.crediya.model.role;

import lombok.Getter;

@Getter
public enum RoleName {
    ADMIN(1),
    ADVISOR(2),
    CLIENT(3);

    private final Integer id;

    RoleName(Integer id) {
        this.id = id;
    }

    public static RoleName fromId(Integer id) {
        for (RoleName role : values()) {
            if (role.id.equals(id)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role ID: " + id);
    }
}
