package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;


@Component
public class PersonValidator implements Validator {

    private final UserServiceImpl userService;

    @Autowired
    public PersonValidator(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User person = (User) target;
        try {
            userService.findByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }
        errors.rejectValue("username", "", "Пользователь с таким именем уже существует");
    }
}
