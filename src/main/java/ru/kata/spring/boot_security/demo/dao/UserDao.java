package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> getAllUsers();

    Long addUser(User user);

    void editUser(User userDetails, Long id);

    void deleteUserById(Long id);

    User getUserById(Long id);

    boolean isTableUsersEmpty();

    Optional<User> getUserByName(String username);
}
