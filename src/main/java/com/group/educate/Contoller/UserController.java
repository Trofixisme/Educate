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
    public String login(@ModelAttribute("user") User user, Model model, HttpSession session) {
        try {
            // 1. Service finds user by email AND verifies the password.
            //    This method should throw an exception on failure (e.g., "incorrect password").
            //    If successful, it returns the fully validated User object.
            User authenticatedUser = userService.login(user.getEmail(), user.getPlainPassword());

            // 2. ONLY if no exception was thrown (authentication succeeded), save the object to the session.
            session.setAttribute("loggedInUser", authenticatedUser);

        } catch (Exception e) {
            // 3. On failure, add the error message and return the login view.
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }
        System.out.println("user logged in");
        // 4. Redirect only on guaranteed SUCCESS.
        return "redirect:/index";
    }
}