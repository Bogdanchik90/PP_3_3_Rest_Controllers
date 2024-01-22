package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.getUserByName(username);
    }

    @Override
    public User findUserById(Long id) {
        if (userDao.getUserById(id) == null) {
            throw new UsernameNotFoundException("Пользователь с таким ID не найден");
        }
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public void update(User updatedUser, Long id) {
        userDao.editUser(updatedUser, id);
    }

    @Transactional
    @Override
    public void add(User user) {
        userDao.addUser(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (userDao.getUserById(id) != null) {
            userDao.deleteUserById(id);
        }
    }

    @Override
    public boolean isTableUsersEmpty() {
        return userDao.isTableUsersEmpty();
    }
}