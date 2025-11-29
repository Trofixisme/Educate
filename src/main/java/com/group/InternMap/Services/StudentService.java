package com.group.InternMap.Services;

import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.Job.PostingType;
import com.group.InternMap.Model.User.Application;
import com.group.InternMap.Model.User.Student;
import com.group.InternMap.Repo.BaseRepository;
import com.group.InternMap.Repo.RepositoryAccessors;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService extends UserService implements FilePaths {
    //todo:need to create an application
    //todo:need to view job postings
    //todo:need to search for job posting ,, soo filtering by name?
    //todo:need to filter
    //todo:updated skill status
    //todo:view roadmap progression

    private final BaseRepository<Application> applicationRepo = new BaseRepository<>(Application.class, applicationPath);
    private final BaseRepository<JobPosting> jobRepo = new BaseRepository<>(JobPosting.class, jobPostingPath);

    public void createApplication(Student student, JobPosting jobPosting) throws Exception {
         List<Application> applications = applicationRepo.findAll();
         Application app = student.submitApplication(jobPosting);
         applications.add(app);
         applicationRepo.saveAll(applications);
    }

    public void deleteApplication(Student student, JobPosting jobPosting) throws Exception {
        List<Application> applications = applicationRepo.findAll();
        applications.removeIf(u -> u.getAssociatedStudent().equals(student) && u.getAssociatedJobPosting().equals(jobPosting));
    }

    public List<JobPosting> findJobposting() throws Exception{
        return jobRepo.findAll();
    }

    public List<JobPosting> findJobpostingByname(String name) throws Exception{
        return RepositoryAccessors.allJobPostings.stream().filter(job -> job.getJobName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    public List<JobPosting> findJobpostingByType(PostingType type) throws Exception{
        return RepositoryAccessors.allJobPostings.stream().filter(job -> job.getJobPostingType().equals(type)).collect(Collectors.toList());
    }
}