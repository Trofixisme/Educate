package com.group.educate.Contoller;

import com.group.educate.Model.User.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String showHomePage() {
        System.out.println("home page accessed ,without session ");
        return "index";
    }
    @GetMapping("/user/home")
    public String showHomePage(Model model, HttpSession session) {
        System.out.println("home page accessed ,with session ");
        // 1. Retrieve the User object from the session
        User user = (User) session.getAttribute("loggedInUser");
        // 2. If no user is found in the session, redirect to the login page
        if (user == null) {
            return "redirect:/login";
        }
        // 3. Pass the user object to the Thymeleaf model for display
        model.addAttribute("user", user);
        return "index";
    }
    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "profile";
    }
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // This removes the session and all attributes, including the "loggedInUser"
        session.invalidate();

        // Redirect the user back to the login page
        return "redirect:/login";
    }

}
