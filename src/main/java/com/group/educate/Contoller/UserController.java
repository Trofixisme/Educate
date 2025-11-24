// src/main/java/com/group/educate/Web/HomeController.java
package com.group.educate.Contoller;

import com.group.educate.Model.User.Student;
import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;
import com.group.educate.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class  UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new Student("Ziad", "Ali", "ziad@example.com",
                "password123", UserRole.STUDENT, 2024, "Cairo University",
                "Computer Science", "FCAI"));
        return "home";  // corresponds to register.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        try {
            userService.register(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/login";
    }
}
