package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.services.PersonServiceImpl;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class AdminController {

    private final String redirect = "redirect:/admin";
    private final PersonServiceImpl personService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminController(PersonServiceImpl personService, RoleServiceImpl roleService) {
        this.personService = personService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("person", personService.getAllPeople());
        return "/hello/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("editUser", personService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "/admin/edit";
    }

    @PatchMapping("/edit/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("editUser") @Valid Person updatePerson,
                         @RequestParam(value = "roles", required = false) Set<Integer> roleIds,
                         BindingResult bindingResult) {


        if (bindingResult.hasErrors())
            return "/admin/edit";

        personService.editUserAndHisRoles(id, updatePerson, roleIds);
        return redirect;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.deleteUserById(id);
        return redirect;
    }
}
