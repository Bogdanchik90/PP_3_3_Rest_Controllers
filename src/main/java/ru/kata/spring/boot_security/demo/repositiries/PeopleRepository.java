package ru.kata.spring.boot_security.demo.repositiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    @Query("Select u from Person u left join fetch u.roles where u.username=:username")
    Optional<Person> findByUsername(String username);

    @Override
    void deleteById(Integer integer);


    @Override
    Person getById(Integer integer);

    @Override
    List<Person> findAll();
}
