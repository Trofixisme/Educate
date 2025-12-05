// src/main/java/com/group/educate/Web/HomeController.java
package com.group.InternMap.Contoller;

import com.group.InternMap.Dto.RecruiterRegistrationDTO;
import com.group.InternMap.Model.User.Admin;
import com.group.InternMap.Model.User.Company.Company;
import com.group.InternMap.Model.User.Company.Recruiter;
import com.group.InternMap.Model.User.Student;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Repo.RepositoryAccessors;
import com.group.InternMap.Services.CompanyService;
import com.group.InternMap.Services.RecruiterService;
import com.group.InternMap.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.group.InternMap.Repo.RepositoryAccessors.allCompanies;
import static com.group.InternMap.Repo.RepositoryAccessors.allUsers;

@Controller
public class UserController {
    private final UserService userService;
    private final RecruiterService recruiterService;
    private final CompanyService companyService;

    public UserController(UserService userService, RecruiterService recruiterService, CompanyService companyService) {
        this.userService = userService;
        this.recruiterService = recruiterService;
        this.companyService = companyService;
    }

//    @GetMapping("/error")
//    public String error() {
//        return "error";
//    }

    @GetMapping("/student/register")
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
    public String showRegisterRecruiter(Model model, RecruiterRegistrationDTO recruiterRegistrationDTO) {
        model.addAttribute("form", new RecruiterRegistrationDTO());
        System.out.println(RepositoryAccessors.allUsers);
        System.out.println(RepositoryAccessors.allCompanies);
        return "RecruiterRegister";
    }

    @PostMapping("/recruiter/register")
    public String registerRecruiter(@ModelAttribute("form") RecruiterRegistrationDTO recruiterRegistrationDTO, Model model) {

        try {
            Company company = recruiterRegistrationDTO.getCompany();
            Recruiter user = recruiterRegistrationDTO.getUser();
//            companyService.findByName(company.getName());
            System.out.println(company);
            // Company returnedCompany = CompanyService.findByName(company.getName());
            System.out.println(company);
            if (company!= null) {
//                user.addCompany(returnedCompany);
                userService.register(user);
                recruiterService.addCompanyToRecruiter(user.getUserID(), company.getName());
                System.out.println(allCompanies);
                System.out.println(allUsers);
            } else {
                userService.register(user);
            }
            // exception here
            System.out.println("After fix");
            return "redirect:/login";
        } catch(Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "RecruiterRegister";
        }
    }

    @GetMapping("/company/register")
    public String showRegisterCompany(Model model) {
        model.addAttribute("company", new Company());
        return "CompanyRegister";
    }

    @PostMapping("/company/register")
    public String RegisterCompany(@ModelAttribute("company") Company company, Model model) {
        try {
            allCompanies.add(company);
            model.addAttribute("success", "Company created successfully.");
            return"redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "CompanyRegister";
        }

    }

    @GetMapping("/admin/register")
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
        return "index";
    }
}