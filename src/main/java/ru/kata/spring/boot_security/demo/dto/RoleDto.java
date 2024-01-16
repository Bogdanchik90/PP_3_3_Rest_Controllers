package ru.kata.spring.boot_security.demo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import ru.kata.spring.boot_security.demo.models.Person;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleDto {
    private int id;

    private String roleName;

    private Set<Person> persons;

    public RoleDto(int id, String roleName, Set<Person> persons) {
        this.id = id;
        this.roleName = roleName;
        this.persons = persons;
    }

    public RoleDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Person> getUsers() {
        return persons;
    }

    public void setUsers(Set<Person> persons) {
        this.persons = persons;
    }

    @JsonProperty("users")
    public List<String> getNamesOfUsersOfRole() {
        return persons.stream().map(Person::getUsername).collect(Collectors.toList());
    }
}
