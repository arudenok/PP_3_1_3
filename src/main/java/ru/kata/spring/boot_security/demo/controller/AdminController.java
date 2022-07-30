package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAll(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        model.addAttribute("users", userService.showUser());
        model.addAttribute("user", userService.getUserByUsername(username));
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "nameRoles", required = false) String roles) {
        user.setRoles(roleService.getByName(roles));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/edit/{id}")
    public String addNewUser(@ModelAttribute("user") User user, @PathVariable("id") int id,
                             @RequestParam(value = "nameRoles", required = false) String roles) {
        user.setRoles(roleService.getByName(roles));
        userService.updateUser(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}
