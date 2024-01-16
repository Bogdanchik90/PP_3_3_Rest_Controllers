package ru.kata.spring.boot_security.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonDto {

    private int id;

    @NotEmpty(message = "имя не должно быть пустым")
    @Size(min = 2, max = 60, message = "имя должно быть от 2 до 60 символов")
    private String username;

    @NotEmpty(message = "фамилия не может быть пустой")
    @Size(min = 2, max = 60, message = "фамилия должна быть от 2 до 60 символов")
    private String lastName;

    @Min(value = 0, message = "возраст не может быть отрицательным")
    private int age;

    @Email
    private String email;

    private String password;

    private Set<Role> roles;

    public PersonDto(int id, String username, String lastName, byte age, String email, String password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public PersonDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @JsonProperty("roles")
    public List<String> getNamesOfRolesOfUser() {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
}