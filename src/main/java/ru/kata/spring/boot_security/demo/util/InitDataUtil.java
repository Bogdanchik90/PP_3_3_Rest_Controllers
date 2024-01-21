package ru.kata.spring.boot_security.demo.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.*;


@Component
public class InitDataUtil {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public InitDataUtil(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {

        if (userService.isTableUsersEmpty()) {
            List<Role> roles = roleService.getAllRole();

            User user1 = new User();
            user1.setUsername("user");
            user1.setLastname("user");
            user1.setAge((byte)10);
            user1.setEmail("user@mail.ru");
            user1.setPassword("user");
            user1.setRoles(Collections.singleton(roles.get(0)));

            User user2 = new User();
            user2.setUsername("admin");
            user2.setLastname("admin");
            user2.setAge((byte)10);
            user2.setEmail("admin@mail.ru");
            user2.setPassword("admin");
            user2.setRoles(Collections.singleton(roles.get(1)));



            userService.add(user1);
            userService.add(user2);
        }

    }
}


