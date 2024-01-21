package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static ru.kata.spring.boot_security.demo.configs.WebSecurityConfig.passwordEncoder;


@Repository
public class UserDaoImpl implements UserDao {


    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public boolean isTableUsersEmpty() {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM User ");
        Long count = (Long) query.getSingleResult();
        return count == 0;
    }


    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public Long addUser(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        User savedUser = entityManager.merge(user);
        return savedUser.getId();
    }

    @Override
    public void editUser(User userDetails) {
        User user = entityManager.find(User.class, userDetails);
        if (user != null) {
            user.setUsername(userDetails.getUsername());
            user.setLastname(userDetails.getLastname());
            user.setAge(userDetails.getAge());
            user.setEmail(userDetails.getEmail());
            user.setRoles(userDetails.getRoles());
            if (!userDetails.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder().encode(userDetails.getPassword()));
            }
            entityManager.merge(user);
        } else {
            throw new RuntimeException("Пользователь с таким id не найден");
        }
    }

    @Override
    public void deleteUserById(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> getUserByName(String username) {
        String query = "SELECT u FROM User u WHERE u.username = :username";
        try {
            User user = entityManager.createQuery(query, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }
}
