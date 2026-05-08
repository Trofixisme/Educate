package com.group.InternMap.Company;

import com.group.InternMap.User.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/company")
public class companyController {
    CompanyService companyService;

    @Autowired
    companyController(CompanyService companyService){
        this.companyService=companyService;
    }

    @PostMapping("/new")
    public void registerCompany(
            @RequestParam("name") String name,
            @RequestParam("industry") String industry,
            @RequestParam(value = "websiteURL", required = false) String websiteURL,
            @RequestParam(value = "locationOfHQ", required = false) String locationOfHQ,
            @RequestParam(value = "logo", required = false) MultipartFile logo,
            Authentication authentication
    ) throws IOException {

        if (authentication == null ||
                !authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.RECRUITER + "]")) {
            throw new RuntimeException("Invalid Role. You must be a recruiter");
        }

        Company company = new Company();
        company.setName(name);
        company.setIndustry(industry);
        company.setWebsiteURL(websiteURL);
        company.setLocationOfHQ(locationOfHQ);

        // ✅ Handle logo upload
        if (logo != null && !logo.isEmpty()) {
            String uploadDir = "uploads/logos/";
            File folder = new File(uploadDir);
            if (!folder.exists()) folder.mkdirs();

            String filename = System.currentTimeMillis() + "_" + logo.getOriginalFilename();
            logo.transferTo(new File(uploadDir + filename));

            company.setLogo("logos/" + filename); // 👈 IMPORTANT
        }

        companyService.save(company);
    }
}
