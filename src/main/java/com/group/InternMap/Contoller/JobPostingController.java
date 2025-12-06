package com.group.InternMap.Contoller;

import com.group.InternMap.Model.User.Application;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Repo.RepositoryAccessors;
import com.group.InternMap.Services.JobPostingService;
import org.springframework.ui.Model;
import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.User.Company.Recruiter;
import com.group.InternMap.Services.RecruiterService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.group.InternMap.Repo.RepositoryAccessors.allJobPostings;

//to do:application controller,and review html
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
        if (session.getAttribute("loggedInUser") == null || !(session.getAttribute("loggedInUser") instanceof Recruiter recruiter)) {
            return "redirect:/login";
        }

        JobPosting jobPosting = new JobPosting();
        jobPosting.setRecruiter(recruiter); // correctly assign recruiter

        model.addAttribute("jobPosting", jobPosting);
        return "JobPostingForm";
    }


    @PostMapping("/JobPostingForm")
    public String saveJobPosting(@ModelAttribute JobPosting jobposting, HttpSession session,Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        Recruiter user = (Recruiter) session.getAttribute("loggedInUser");
//        jobposting.setRecruiterEmail(user.getEmail());
        try { // link posting to recruiter
            allJobPostings.add(jobposting);
            jobposting.setRecruiter(user);

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
//    @GetMapping("/applications")
//    public String createNewApplication( Application application, Model model, HttpSession session) {
//        if (session.getAttribute("loggedInUser") == null) {
//            return "redirect:/login";
//        }
//        model.addAttribute("application", new Application());
//        return "Application";
//    }
//    @PostMapping("/application/save")
//    public String saveApplication(@ModelAttribute Application application,JobPosting jobPosting, Model model, HttpSession session) {
//        if (session.getAttribute("loggedInUser") == null) {
//            return "redirect:/login";
//        }
//
//        try {
//            if (application != null) {
//                model.addAttribute("success", "you have applied successfully");
//                model.addAttribute("application", application);
//                jobPosting.setApplication(application);
//                return "redirect:/jobPostings";
//            }
//        }
//        catch (Exception e) {
//            model.addAttribute("error", "Error saving application: " + e.getMessage());
//            return "JobPostingForm";
//        }
//        return "JobPosting";
//
//    }
@GetMapping("/recruiter/jobpostings")
public String getRecruiterJobPostings(Model model, HttpSession session) throws Exception {

    Recruiter recruiter = (Recruiter) session.getAttribute("loggedInUser");
    if (recruiter == null) {
        return "redirect:/login";
    }
    List<JobPosting> myJobs = jobPostingService.getJobPostingsByRecruiterId(recruiter.getUserID());
    model.addAttribute("myJobs", myJobs);
    return "recruiter-jobpostings";
}



@GetMapping("/JobPostings/{jobId}/applications")
public String viewApplications(@PathVariable UUID jobId,
                               Model model,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        System.out.println("before");
    Recruiter recruiter = (Recruiter) session.getAttribute("loggedInUser");
    if (recruiter == null) {
        return "redirect:/login";
    }

    try {
        JobPosting job = jobPostingService.findByID(jobId);
        if (job == null) {
            System.out.println("job is null");
            redirectAttributes.addFlashAttribute("error", "Job not found");
            return "redirect:/JobPostings";
        }
        System.out.println("after");
        List<Application> apps = recruiterService.getApplicationsByJobPosting(job);
        model.addAttribute("jobPosting", job);
        model.addAttribute("applications", apps);
        return "ViewApplicationDetail";//i still dont have it but need to do it for clicking the view button

    } catch (Exception e) {
        System.out.println("error");
        redirectAttributes.addFlashAttribute("error", "Error loading applications");
        return "redirect:/JobPostings";
    }
}
    @GetMapping("/cv/{email}")
    public String viewCV(@PathVariable("email") String email, Model model, HttpSession session) {
        try {
            System.out.println(email);
            Application application = recruiterService.findByEmail(email);
            model.addAttribute("application", application);
            return "profile";
        }
        catch(Exception e) {
            model.addAttribute("error", "Error loading application");
            return "ViewApplicationDetail";
        }
    }


}




