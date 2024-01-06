package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Person;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PersonDao {
    List<Person> getAllUsers();

    void addUser(Person person, Set<Integer> roleIds);

    void editUserAndHisRoles(int id, Person personDetails, Set<Integer> roleIds);

    void deleteUserById(int id);

    Person getUserById(int id);

    Optional<Person> getPersonByName(String username);
}
