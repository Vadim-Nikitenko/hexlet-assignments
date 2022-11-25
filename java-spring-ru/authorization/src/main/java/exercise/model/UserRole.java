package exercise.model;

import lombok.Getter;

// Возможные роли для пользователя
@Getter
public enum UserRole {
    ADMIN(0),
    USER(1);

    private final int name;

    UserRole(int name) {
        this.name = name;
    }
}
