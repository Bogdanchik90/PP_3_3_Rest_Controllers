package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
  private final UserDao userDao;

  @Autowired
  public UserDetailServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userDao.getUserByName(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("Пользователь с таким именем не найден");
    }
    return user.orElse(null);
  }
}