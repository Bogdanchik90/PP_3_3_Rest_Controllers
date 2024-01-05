package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositiries.PeopleRepository;
import ru.kata.spring.boot_security.demo.security.PersonDetailsImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ru.kata.spring.boot_security.demo.configs.WebSecurityConfig.passwordEncoder;

@Service
public class PersonServiceImpl implements PersonService, UserDetailsService {
    private PeopleRepository peopleRepository;
    private final RoleServiceImpl roleService;

    @Autowired
    public PersonServiceImpl(PeopleRepository peopleRepository, RoleServiceImpl roleService) {
        this.peopleRepository = peopleRepository;
        this.roleService = roleService;
    }


    @Override
    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    @Override
    @Transactional
    public void addUser(Person person, Set<Integer> roleIds) {
        if (roleIds != null) {
            Set<Role> selectedRole = roleService.getRolesByIds(roleIds);
            person.setRoles(selectedRole);
        }
        person.setPassword(passwordEncoder().encode(person.getPassword()));
        peopleRepository.save(person);
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        peopleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void editUserAndHisRoles(int id, Person personDetails, Set<Integer> roleIds) {
        Optional<Person> optionalPerson = peopleRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();

            person.setUsername(personDetails.getUsername());
            person.setLastName(personDetails.getLastName());
            person.setAge(personDetails.getAge());
            person.setEmail(personDetails.getEmail());
            if (roleIds != null) {
                Set<Role> selectedRole = roleService.getRolesByIds(roleIds);
                person.setRoles(selectedRole);
            }
            if (!personDetails.getPassword().isEmpty()) {
                person.setPassword(passwordEncoder().encode(personDetails.getPassword()));
            }
            peopleRepository.save(person);
        } else {
            throw new RuntimeException("Пользователь с таким id не найден");
        }
    }

    @Override
    public Person getUserById(int id) {
        return peopleRepository.getById(id);
    }


    public Optional<Person> getPersonByName(String username) {
        return peopleRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);

        if (person.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return new PersonDetailsImpl(person.get());
    }
}
