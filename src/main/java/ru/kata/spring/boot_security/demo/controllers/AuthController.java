package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.services.PersonDetailsService;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.util.PersonValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonValidator personValidator;
    private final PersonDetailsService personDetailsService;
    private final RoleService roleService;

    @Autowired
    public AuthController(PersonValidator personValidator,
                          PersonDetailsService personDetailsService, RoleService roleService) {
        this.personValidator = personValidator;
        this.personDetailsService = personDetailsService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/auth/login";
    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "/auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      @RequestParam(value = "roles",required = false) List<Integer> roleIds,
                                      BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/registration";

        personDetailsService.register(person, roleIds);
        return "redirect:/auth/login";
    }
}
