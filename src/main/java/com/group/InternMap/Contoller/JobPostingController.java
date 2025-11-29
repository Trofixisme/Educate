package com.group.InternMap.Contoller;

import com.group.InternMap.Model.User.User;
import com.group.InternMap.Services.JobPostingService;
import org.springframework.ui.Model;
import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.User.Company.Recruiter;
import com.group.InternMap.Services.RecruiterService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class JobPostingController {
    private final RecruiterService recruiterService;

    public JobPostingController(RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    @GetMapping("/JobPosting")
    public String JobPosting(){
        return "JobPosting";
    }
     @GetMapping("/JobPosting")
    public String AddJobPosting(JobPosting jobposting, Model model1, Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {return "redirect:/login";}
         User user = (Recruiter) session.getAttribute("loggedInUser");
        try {
            model1.addAttribute("user",user);
            model.addAttribute("jobposting", new JobPosting());
            recruiterService.addJobPosting(jobposting);
        } catch(Exception e){
            e.printStackTrace();
        }
        return "JobPosting";
    }

    public String SearchJobPosting(JobPosting jobposting, Model model1, Model model, HttpSession session) {
        JobPostingService jobPostingService;
        if (session.getAttribute("loggedInUser") == null) {return "redirect:/login";}
        User user= (Recruiter) session.getAttribute("loggedInUser");
    }

}
