package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.services.PersonDetailsService;

import java.security.Principal;

@Controller
public class HelloController {

    private final PersonDetailsService personDetailsService;

    public HelloController(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/hello")
    public String sayHello(Model model, Principal principal) {
        Person person = personDetailsService.getPersonByName(principal.getName()).orElse(null);

        model.addAttribute("person", person);
        return "/hello/index";
    }

}
