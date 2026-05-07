package com.group.InternMap.Company;

import com.group.InternMap.User.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
public class companyController {
    CompanyService companyService;

    @Autowired
    companyController(CompanyService companyService){
        this.companyService=companyService;
    }

    @PostMapping("/new")
    public void RegisterCompany(@RequestBody Company company, Authentication authentication) {
        try {
            if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.RECRUITER + "]")) {
                companyService.save(company);
            } else {
                throw new RuntimeException("Invalid Role. You must be a recruiter to register a company");
            }
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new RuntimeException("A company with this name already exists");
            } else {
                throw new RuntimeException("Required Data Missing");
            }
        }
    }
}
