package com.group.educate.Services;

import com.group.educate.Model.Job.JobPosting;
import com.group.educate.Model.Job.PostingType;
import com.group.educate.Model.User.Application;
import com.group.educate.Model.User.Student;
import com.group.educate.Repo.BaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService extends UserService{
    //todo:need to create an application
    //todo:need to view job postings
    //todo:need to search for job posting ,, soo filtering by name?
    //todo:need to filter
    //todo:updated skill status
    //todo:view roadmap progression


    private static final String fileNameJob = "data/jobs.txt";
    private static final String fileNameJApplication = "data/applications.txt";
    private final BaseRepository<Application> applicationRepo = new BaseRepository<>(Application.class, fileNameJApplication);
    private final BaseRepository<JobPosting> jobRepo = new BaseRepository<>(JobPosting.class, fileNameJob);

    public void creatApplication(Student student, JobPosting jobPosting) throws Exception {
        List<Application> applications = applicationRepo.findAll();
         Application app= student.submitApplication(jobPosting);
         applications.add(app);
    }
    public List<JobPosting> findJobposting() throws Exception{
        return jobRepo.findAll();
    }

    public List<JobPosting> findJobpostingByname(String name) throws Exception{
        List<JobPosting> jobPostings =  jobRepo.findAll();
        return jobPostings.stream().filter(job -> job.getJobName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    public List<JobPosting> findJobpostingByType(PostingType type) throws Exception{
        List<JobPosting> jobPostings = jobRepo.findAll();
        return jobPostings.stream().filter(job -> job.getJobType().equals(type)).collect(Collectors.toList());
    }

}
