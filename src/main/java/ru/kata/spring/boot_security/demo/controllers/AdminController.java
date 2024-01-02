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
public class AdminController {

    private final PersonDetailsService personDetailsService;
    private final PersonValidator personValidator;
    private final RoleService roleService;

    @Autowired
    public AdminController(PersonDetailsService personDetailsService, PersonValidator personValidator, RoleService roleService) {
        this.personDetailsService = personDetailsService;
        this.personValidator = personValidator;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("person", personDetailsService.getAllPeople());
        return "/hello/admin";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam("id") int id, Model model) {
        model.addAttribute("editUser", personDetailsService.getById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "/admin/edit";
    }

    @PatchMapping("/edit")
    public String update(@RequestParam("id") int id, @ModelAttribute("editUser") @Valid Person updatePerson,
                         @RequestParam(value = "roles", required = false) List<Integer> roleIds,
                         BindingResult bindingResult) {

        personValidator.validate(updatePerson, bindingResult);

        if (bindingResult.hasErrors() && !(updatePerson.getPassword().isEmpty()))
            return "/admin/edit";

        personDetailsService.updateUserById(id, updatePerson, roleIds);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        personDetailsService.deleteById(id);
        return "redirect:/admin";
    }
}
