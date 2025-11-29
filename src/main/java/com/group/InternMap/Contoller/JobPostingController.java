package com.group.InternMap.Contoller;

import com.group.InternMap.Repo.RepositoryAccessors;
import com.group.InternMap.Services.JobPostingService;
import org.springframework.ui.Model;
import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.User.Company.Recruiter;
import com.group.InternMap.Services.RecruiterService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller

public class JobPostingController {
    private final RecruiterService recruiterService;
    private final JobPostingService jobPostingService;

    public JobPostingController(RecruiterService recruiterService, JobPostingService jobPostingService) {
        this.recruiterService = recruiterService;
        this.jobPostingService = jobPostingService;
    }
   //JobPostings
    @GetMapping("/JobPostings")
    public String getAllJobPostings(Model model, ArrayList<JobPosting> jobposting, HttpSession session) {
        // Optional: check if user is logged in
//        if()
//        Recruiter user = (Recruiter) session.getAttribute("loggedInUser");
//        jobposting.setRecruiter(user); // link the job posting to the recruiter
//        RepositoryAccessors.allJobPostings.add(jobposting);

        try {
            // Fetch all job postings from the service
            jobposting = (ArrayList<JobPosting>) jobPostingService.getAllJobPostings();
            System.out.println("before adding");
            model.addAttribute("jobPostings", jobposting);
            System.out.println("after adding");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to load job postings");
            System.out.println("entered catch");
        }
        System.out.println("before returning");
        return "JobPosting"; // Thymeleaf template
    }

    @GetMapping("/JobPostingForm")
    public String AddJobPostingForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null || !(session.getAttribute("loggedInUser") instanceof Recruiter user)) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("jobposting", new JobPosting());

        return "JobPostingForm"; // Thymeleaf template to display the form
    }

    @PostMapping("/JobPostingForm")
    public String saveJobPosting(@ModelAttribute JobPosting jobposting, HttpSession session,Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        Recruiter user = (Recruiter) session.getAttribute("loggedInUser");
//        jobposting.setRecruiterEmail(user.getEmail());
        try { // link posting to recruiter
            RepositoryAccessors.allJobPostings.add(jobposting);
            return "redirect:/JobPostings";

        }
        catch (Exception e) {
            model.addAttribute("error", "Failed to add job posting");
            return "JobPostingForm";
        }
    }

    @PostMapping("/searchJobPosting")
    public String searchJobPosting(@ModelAttribute JobPosting jobposting, Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        try {
            // Search dynamically using your service
            List<JobPosting> results = jobPostingService.searchJobPostings(
                    jobposting.getCompanyName(),
                    jobposting.getRecruiter().getEmail()
            );
            // Add search results to the model
            model.addAttribute("results", results);
            // Add the jobposting object to the model so form fields keep their values
            model.addAttribute("jobposting", jobposting);
        } catch (Exception e) {
            model.addAttribute("error", "Error searching job postings: " + e.getMessage());
        }
        return "searchResult"; // Thymeleaf template
    }
}
