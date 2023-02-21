package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping()
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") User user)
    {
        Role role = new Role("ROLE_USER");
        roleService.saveRole(role);
        user.setRoles(Set.of(role));
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/login";
    }

    @GetMapping("/user")
    public String getUserPage(Model model,Principal principal) {
        Long id = userService.getUserByUsername(principal.getName()).getId();
        model.addAttribute("user", userService.getUserById(id));
        return "/show_user";
    }
}
