package ru.practicum.shareit.user.model;

/**
 * TODO Sprint add-controllers.
 */

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
