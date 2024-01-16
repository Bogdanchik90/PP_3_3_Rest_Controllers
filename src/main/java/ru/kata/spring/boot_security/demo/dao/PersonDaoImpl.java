package ru.kata.spring.boot_security.demo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.services.RoleService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ru.kata.spring.boot_security.demo.configs.WebSecurityConfig.passwordEncoder;

@Repository
public class PersonDaoImpl implements PersonDao {

    private final RoleService roleService;

    public PersonDaoImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public boolean isTableUsersEmpty() {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM Person");
        Long count = (Long) query.getSingleResult();
        return count == 0;
    }

    @Override
    public List<Person> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM Person u", Person.class).getResultList();
    }

    @Override
    public Integer addUser(Person person, Set<Integer> roleIds) {
        if (roleIds != null) {
            Set<Role> selectedRole = roleService.getRolesByIds(roleIds);
            person.setRoles(selectedRole);
        }
        person.setPassword(passwordEncoder().encode(person.getPassword()));
        Person savedPerson = entityManager.merge(person);
        return savedPerson.getId();
    }

    @Override
    public void editUserAndHisRoles(int id, Person personDetails, Set<Integer> roleIds) {
        Person person = entityManager.find(Person.class, id);
        if (person != null) {
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
            entityManager.merge(person);
        } else {
            throw new RuntimeException("Пользователь с таким id не найден");
        }
    }

    @Override
    public void deleteUserById(int id) {
        entityManager.remove(entityManager.find(Person.class, id));
    }
    @Override
    public Optional<Person> getPersonByName(String username) {
        String query = "SELECT p FROM Person p WHERE p.username = :username";
        try {
            Person person = entityManager.createQuery(query, Person.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(person);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Person getUserById(int id) {
        return entityManager.find(Person.class, id);
    }
}
