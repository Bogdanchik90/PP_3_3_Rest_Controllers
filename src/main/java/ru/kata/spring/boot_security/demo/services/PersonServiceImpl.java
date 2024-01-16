package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.PersonDao;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.security.PersonDetailsImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class PersonServiceImpl implements PersonService, UserDetailsService {
    private PersonDao personDao;

    @Autowired
    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }


    @Override
    public List<Person> getAllPeople() {
        return personDao.getAllUsers();
    }

    @Override
    @Transactional
    public Integer addUser(Person person, Set<Integer> roleIds) {
        return personDao.addUser(person, roleIds);
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        personDao.deleteUserById(id);
    }

    @Override
    @Transactional
    public void editUserAndHisRoles(int id, Person personDetails, Set<Integer> roleIds) {
        personDao.editUserAndHisRoles(id, personDetails, roleIds);
    }

    @Override
    public Person getUserById(int id) {
        return personDao.getUserById(id);
    }

    @Override
    public Optional<Person> getPersonByName(String username) {
        return personDao.getPersonByName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personDao.getPersonByName(username);

        if (person.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return new PersonDetailsImpl(person.get());
    }

    @Override
    public boolean isTableUsersEmpty() {
        return personDao.isTableUsersEmpty();
    }
}
