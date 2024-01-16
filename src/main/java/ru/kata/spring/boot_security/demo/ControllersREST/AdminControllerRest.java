package ru.kata.spring.boot_security.demo.ControllersREST;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.PersonDto;
import ru.kata.spring.boot_security.demo.dto.RoleDto;
import ru.kata.spring.boot_security.demo.exception_handling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.services.PersonService;
import ru.kata.spring.boot_security.demo.services.RoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AdminControllerRest {

    private final PersonService personService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    @Autowired
    public AdminControllerRest(PersonService personService, RoleService roleService, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.personService = personService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/users")
    public List<Person> showAllUsers() {
        return personService.getAllPeople();
    }

    @GetMapping("/users/{id}")
    public Person showUserById(@PathVariable int id) {
        Person person = personService.getUserById(id);
        if (person == null) {
            throw new NoSuchUserException("Пользователя с id " + id + " нет в базе данных");
        }
        return person;
    }

    @PostMapping("/users")
    public Integer addPerson(@RequestBody Map<String, Object> requestBody) {
        Person person = objectMapper.convertValue(requestBody.get("newUser"), Person.class);
        List<Integer> rolesIdsList = ((List<?>)requestBody.get("roleIds")).stream()
                .map(Object::toString)
                .map(Integer::parseInt)
                .toList();
        Set<Integer> rolesIds = new HashSet<>();
        rolesIds.addAll(rolesIdsList);

        return personService.addUser(person, rolesIds);
    }
    @PatchMapping("/users/{id}")
    public void editUser(@PathVariable("id") int id, @RequestBody Map<String, Object> requestBody) {
        Person person = objectMapper.convertValue(requestBody.get("editUser"), Person.class);
        List<Integer> rolesIdsList = ((List<?>)requestBody.get("roleIds")).stream()
                .map(Object::toString)
                .map(Integer::parseInt)
                .toList();
        Set<Integer> rolesIds = new HashSet<>();
        rolesIds.addAll(rolesIdsList);

        personService.editUserAndHisRoles(id,person,rolesIds);
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        personService.deleteUserById(id);
    }


    private Person convertToPerson(PersonDto personDto) {
        return modelMapper.map(personDto, Person.class);
    }

    private PersonDto convertToUserDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }

    private Role convertToRole(RoleDto roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }

    private RoleDto convertToRoleDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

}
