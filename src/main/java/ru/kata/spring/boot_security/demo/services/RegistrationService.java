package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.repositiries.PeopleRepository;

import static ru.kata.spring.boot_security.demo.configs.WebSecurityConfig.passwordEncoder;

@Service
public class RegistrationService {
    private PeopleRepository peopleRepository;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder().encode(person.getPassword()));
        peopleRepository.save(person);
    }
}
