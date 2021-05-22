package ru.geekbrains.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persistence.entities.User;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("")
    public String editUsersList() {
        return "Edit Users List / страница со списком пользователей";
    }

    @GetMapping("/edit/{userId}")
    public String editUser(@PathVariable Long userId) {
        return "Edit Users Form / форма редактирование данных о пользователе с id = " + userId;
    }

    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createUser(@RequestBody User user) {
        return "Save User Page / сохранить данные о пользователе: " + user.getUsername() + " " + user.getEmail();

    }

    @PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateUser(@RequestBody User user) {
        return "Update User Page / изменить данные о пользователе " + user.getUsername() + " " + user.getEmail();
    }

    @DeleteMapping(value = "/edit/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deleteUser(@PathVariable Long userId) {
        return "Delete User Page / удалить данные о пользователе id = " + userId;
    }
}
