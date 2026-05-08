package com.group.InternMap.Company;

import com.group.InternMap.Recruiter.Recruiter;
import com.group.InternMap.Recruiter.RecruiterRepo;
import com.group.InternMap.User.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import java.nio.file.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/company")
public class companyController {
    CompanyService companyService;
    RecruiterRepo recruiterRepo;

    @Autowired
    companyController(CompanyService companyService, RecruiterRepo recruiterRepo ) {
        this.companyService=companyService;
        this.recruiterRepo=recruiterRepo;
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


        if (logo != null && !logo.isEmpty()) {
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "logos");
            Files.createDirectories(uploadPath);

            String filename = System.currentTimeMillis() + "_" + logo.getOriginalFilename();
            Path targetPath = uploadPath.resolve(filename);
            logo.transferTo(targetPath.toAbsolutePath());
            company.setLogo("logos/" + filename);
        }

        Company savedCompany = companyService.save(company);

        Recruiter recruiter = recruiterRepo.findByEmail(authentication.getName());
        if (recruiter == null) {
            throw new RuntimeException("Recruiter not found");
        }
        recruiter.addCompany(savedCompany);
        recruiterRepo.save(recruiter);
    }
}
