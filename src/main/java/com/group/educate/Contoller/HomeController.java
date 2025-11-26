package com.group.educate.Contoller;

import com.group.educate.Model.User.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }
    @GetMapping("/")
    public String showHomePage(Model model, HttpSession session) {

        // 1. Retrieve the User object from the session
        User user = (User) session.getAttribute("loggedInUser");

        // 2. If no user is found in the session, redirect to the login page
        if (user == null) {
            return "redirect:/login";
        }

        // 3. Pass the user object to the Thymeleaf model for display
        model.addAttribute("user", user);

        return "home";
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

}
