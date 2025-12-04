package com.group.InternMap.Contoller;

import com.group.InternMap.Dto.ApplicationandCVDTO;
import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.User.Application;
import com.group.InternMap.Model.User.CV;
import com.group.InternMap.Model.User.Student;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Repo.RepositoryAccessors;
import com.group.InternMap.Services.JobPostingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

import static com.group.InternMap.Repo.RepositoryAccessors.*;

@Controller
public class ApplicationController {
    JobPostingService jobPostingService;
    ApplicationController(JobPostingService jobPostingService){
        this.jobPostingService = jobPostingService;
    }

    @GetMapping("/cv")
    public String cv(Model model, HttpSession session) {
        // Fixed: Use correct attribute name
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        if (!(user instanceof Student)) {
            return "redirect:/profile";
        }
        Student student = (Student) user;
        if (student.getCv() != null) {
            model.addAttribute("cv", student.getCv());
        } else {
            model.addAttribute("cv", new CV());
        }
        // Fixed: Return the form view
        return "CV";
    }
    @PostMapping("/cv/save")
    public String saveCV(@ModelAttribute("cv") CV cv, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedInUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        if (!(loggedUser instanceof Student)) {
            return "redirect:/profile";
        }

        Student student = (Student) loggedUser;
        if(allUsers.contains(student)) {
//            allUsers.remove(student);
            if (student.getCv() != null) {
                System.out.println(student.getCv());
                CV existingCV = student.getCv();
                existingCV.setDescription(cv.getDescription());
                existingCV.setPastExperiences(cv.getPastExperiences());
                existingCV.setProjects(cv.getProjects());
                student.setCv(existingCV);
                System.out.println(existingCV);
//                allUsers.add(student);
            } else {
                cv.setStudent(student);
                student.setCv(cv);
//                allUsers.add(student);

            }
        }


        // Update session
        session.setAttribute("loggedInUser", student);

        return "redirect:/profile";
    }

    @GetMapping("/applications/new")
    public String createNewApplication(@RequestParam("jobId") UUID jobPostingId, ApplicationandCVDTO applicationandCVDTO, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        model.addAttribute("jobId", jobPostingId);
        model.addAttribute("applicationandCVDTO", new ApplicationandCVDTO());
        model.addAttribute("jobPosting", jobPosting); // Add job details to display
        return "Application";
    }

    @PostMapping("/application/save/{jobId}")
    public String saveApplication(@PathVariable("jobId") UUID jobId, @ModelAttribute ApplicationandCVDTO applicationandCVDTO , Model model, HttpSession session) {
        Application application = applicationandCVDTO.getApplication();

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        Student user = (Student) session.getAttribute("loggedInUser");
        applicationandCVDTO.setStudent(user);
        UUID jobPostingID = jobId;

        try {
            if (user.getCv() != null) {
                application.setCv(user.getCv());
                applicationandCVDTO.setApplication(application);
                JobPosting jobPosting = jobPostingService.findByID(jobPostingID);
                allApplications.add(application);
                jobPosting.setApplication(application);
                allJobPostings.add(jobPosting);
                model.addAttribute("success", "you have applied successfully");
                return "redirect:/jobPostings";
            }
            else{
                return "redirect:/cv";
            }

        }
        catch (Exception e) {
            model.addAttribute("error", "Error saving application: " + e.getMessage());
            return "JobPostingForm";
        }

    }

}
