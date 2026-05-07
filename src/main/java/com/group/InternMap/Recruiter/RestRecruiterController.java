package com.group.InternMap.Recruiter;

import com.group.InternMap.Application.ApplicationRepo;
import com.group.InternMap.Company.CompanyService;
import com.group.InternMap.DTO.RecruiterRegistrationDTO;
import com.group.InternMap.Job.JobPostingService;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/recruiter")
public class RestRecruiterController {

    CompanyService companyService;
    RecruiterService recruiterService;
    UserService userService;
    JobPostingService jobPostingService;
    ApplicationRepo applicationRepo;

    RecruiterRepo recruiterRepo;

    Logger logger = LoggerFactory.getLogger(RecruiterController.class);

    @Autowired
    public RestRecruiterController(RecruiterService recruiterService, CompanyService companyService, UserService userService, JobPostingService jobPostingService, ApplicationRepo appRepo, RecruiterRepo recruiterRepo) {
        this.recruiterService = recruiterService;
        this.userService = userService;
        this.companyService = companyService;
        this.jobPostingService = jobPostingService;
        this.applicationRepo = appRepo;
        this.recruiterRepo = recruiterRepo;
    }

    @PostMapping("/register")
    public void registerRecruiter(HttpServletRequest request, @RequestBody RecruiterRegistrationDTO dto) throws ServletException, DataIntegrityViolationException {
        recruiterService.registerRecruiter(dto, request);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateRecruiter(@RequestBody Recruiter recruiter, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.RECRUITER + "]")) {
            Recruiter recruiterToUpdate = recruiterRepo.findByEmail(authentication.getName());
            recruiterService.updateRecruiter(recruiterToUpdate, recruiter);
        }

        return ResponseEntity.ok(Map.of("token", ""));
    }
}