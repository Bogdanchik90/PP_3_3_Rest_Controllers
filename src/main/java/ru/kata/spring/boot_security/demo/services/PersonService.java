package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Person;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PersonService {
    List<Person> getAllPeople();

    Integer addUser(Person person, Set<Integer> roleIds);

    void deleteUserById(int id);

    void editUserAndHisRoles(int id, Person personDetails, Set<Integer> roleIds);

    Person getUserById(int id);
    Optional<Person> getPersonByName(String username);

    boolean isTableUsersEmpty();

}
