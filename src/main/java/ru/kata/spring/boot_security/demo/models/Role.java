package ru.kata.spring.boot_security.demo.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<Person> persons = new HashSet<>();

    public Role() {
    }

    public Role(int id, String name, Set<Person> persons) {
        this.id = id;
        this.name = name;
        this.persons = persons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        if (name.equals("ROLE_ADMIN")) {
            return "ADMIN";
        } else if (name.equals("ROLE_USER")) {
            return "USER";
        } else {
            return "ADMIN, USER";
        }
    }
}
