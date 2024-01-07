package ru.kata.spring.boot_security.demo.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.services.PersonServiceImpl;

import javax.annotation.PostConstruct;

import java.util.Collections;
import java.util.Set;


@Component
public class InitDataUtil {

    private final PersonServiceImpl personService;

    @Autowired
    public InitDataUtil(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @PostConstruct
    public void init() {

        if (personService.isTableUsersEmpty()) {
            Person person1 = new Person();
            person1.setUsername("user");
            person1.setLastName("user");
            person1.setAge(10);
            person1.setEmail("user@mail.ru");
            person1.setPassword("user");


            Person person2 = new Person();
            person2.setUsername("admin");
            person2.setLastName("admin");
            person2.setAge(10);
            person2.setEmail("admin@mail.ru");
            person2.setPassword("admin");

            Set<Integer> user = Collections.singleton(1);
            Set<Integer> admin = Collections.singleton(2);

            personService.addUser(person1, user);
            personService.addUser(person2, admin);
        }

    }
}


