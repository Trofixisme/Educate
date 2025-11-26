// src/main/java/com/group/educate/Web/HomeController.java
package com.group.educate.Contoller;
import com.group.educate.Model.User.User;
import com.group.educate.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute("user", new User());
        return "error";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register_form";
    }

    @PostMapping("/users/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            userService.register(user);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            // 2. Return the view name (DO NOT REDIRECT)
            return "register_form";
        }
        // Only redirect on SUCCESS
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showloginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }




    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session) {
        try {
            // 1. The service returns success if credentials are valid.
            //    (You would refactor your service to return the User object here, not just "user found")
            User authenticatedUser = userService.searchByEmail(user.getEmail()); // Assumes a new service method
            // 2. Store the User object in the Session
            session.setAttribute("loggedInUser", authenticatedUser);

        } catch (Exception e) {
            // Handle login failure...
            // ...
            return "login";
        }

        // Redirect to the secured home page
        return "redirect:/home";
    }

}