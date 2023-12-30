package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.repositiries.PeopleRepository;
import ru.kata.spring.boot_security.demo.security.PersonDetails;

import java.util.List;
import java.util.Optional;

import static ru.kata.spring.boot_security.demo.configs.WebSecurityConfig.passwordEncoder;

@Service
public class PersonDetailsService implements UserDetailsService {
    private PeopleRepository peopleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);

        if (person.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return new PersonDetails(person.get());
    }
    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    public Optional<Person> getPersonByName(String username) {
        return peopleRepository.findByUsername(username);
    }
    @Transactional
    public void deleteById(int id) {
        peopleRepository.deleteById(id);
    }
    @Transactional
    public void updateUserById(int id, Person personDetails) {
        Optional<Person> optionalPerson = peopleRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();

            person.setUsername(personDetails.getUsername());
            person.setLastName(personDetails.getLastName());
            person.setAge(personDetails.getAge());
            person.setEmail(personDetails.getEmail());
            person.setRole(personDetails.getRole());
            person.setPassword(passwordEncoder.encode(person.getPassword()));

            peopleRepository.save(person);
        } else {
            throw new RuntimeException("Пользователь с таким id не найден");
        }
    }

    public Person getById(int id) {
        return peopleRepository.getById(id);
    }
}
