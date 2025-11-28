// src/main/java/com/group/educate/Web/HomeController.java
package com.group.educate.Contoller;

import com.group.educate.Model.User.Admin;
import com.group.educate.Model.User.Company.Company;
import com.group.educate.Model.User.Company.Recruiter;
import com.group.educate.Model.User.Student;
import com.group.educate.Model.User.User;
import com.group.educate.Services.RecruiterService;
import com.group.educate.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;
    private final RecruiterService recruiterService;

    public UserController(UserService userService, RecruiterService recruiterService) {
        this.userService = userService;
        this.recruiterService = recruiterService;
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/Student/register")
    public String showRegisterStudent(Model model) {
        model.addAttribute("user", new Student());
        return "StudentRegister";
    }

    @PostMapping("/student/register")
    public String registerStudent(@ModelAttribute("user") Student user, Model model) {
        try {
            userService.register(user);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            // 2. Return the view name (DO NOT REDIRECT)
            return "StudentRegister";
        }
        // Only redirect on SUCCESS
        return "redirect:/login";
    }

    @GetMapping("/recruiter/register")
    public String showRegisterRecruiter(Model model) {
        model.addAttribute("user", new Recruiter());
        return "RecruiterRegister";
    }

    @PostMapping("/recruiter/register")
    public String registerRecruiter(@ModelAttribute("user") Recruiter user, @ModelAttribute("company") Company company, Model model) {
//        try {
//            userService.register(user);
//            System.out.println(company);
//            System.out.println(recruiterService.viewAllCompanies());
//                if (recruiterService.viewAllCompanies().contains(company)) {
//                    user.addCompany(company);
//                    company.addRecruiter(user);
//
//                    model.addAttribute("success", "Recruiter created successfully.");
//                    return "redirect:/login";
//                } else if (company.getName() == null || company.getName().isBlank()) {
//                    return "redirect:/login";
//                }
//
//            return "redirect:/Company/Register";
//
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            // 2. Return the view name (DO NOT REDIRECT)
//            return "RecruiterRegister";
//        }
        try {
            userService.register(user);
            if (company.getCompanyID() == null)
                recruiterService.addCompany(company);
            recruiterService.addCompanyToRecruiter(user.getUserID(), company.getCompanyID());
            return "redirect:/login";
        }catch(Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "RecruiterRegister";
        }
    }

    @GetMapping("/Company/Register")
    public String showRegisterCompany(Model model) {
        model.addAttribute("company", new Company());
        return "CompanyRegister";
    }

    @PostMapping("/Company/Register")
    public String RegisterCompany(@ModelAttribute("company") Company company, Model model) {
        try {
            recruiterService.addCompany(company);
            model.addAttribute("success", "Company created successfully.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "CompanyRegister";
        }

        return "redirect:/recruiter/register";
    }

    @GetMapping("/Admin/register")
    public String showRegisterAdmin(Model model) {
        model.addAttribute("user", new Admin());
        return "adminRegister";
    }

    @PostMapping("/admin/register")
    public String registerAdmin(@ModelAttribute("user") Admin user, Model model) {
        try {
            userService.register(user);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            // 2. Return the view name (DO NOT REDIRECT)
            return "adminRegister";
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