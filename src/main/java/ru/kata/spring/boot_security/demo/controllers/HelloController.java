package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.services.PersonServiceImpl;

import java.security.Principal;

@Controller
public class HelloController {

    private final PersonServiceImpl personService;

    public HelloController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @GetMapping("/hello")
    public String sayHello(Model model, Principal principal) {
        Person person = personService.getPersonByName(principal.getName()).orElse(null);

        model.addAttribute("person", person);
        return "/hello/index";
    }

}
