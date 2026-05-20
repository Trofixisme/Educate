package com.group.InternMap.Job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class JobPostingService {

    JobRepo jobRepo;

    @Autowired
    public JobPostingService(JobRepo jobRepo) {
        this.jobRepo = jobRepo;
    }

    public List<JobPosting> getAllJobPostings()  {
        return jobRepo.findAllByOrderByDatePostedDesc();
    }

    public List<JobPosting> getJobPostingsByRecruiterId(long recruiterId)  {
        return jobRepo.findJobPostingByRecruiterIdOrderByDatePostedDesc(recruiterId);
    }

    public List<JobPosting> getAllJobPostingsName(String name) {
        return jobRepo.findJobPostingByJobNameOrderByDatePostedDesc(name);
    }

    public JobPosting findJobPostingByID(long appId) {
        return jobRepo.findJobPostingById(appId);
    }

    public JobPosting createJobPosting(JobPosting jobPosting) {
        return jobRepo.save(jobPosting);
    }

    public void deleteJobPosting(JobPosting jobPosting){
         jobRepo.delete(jobPosting);
    }

    public List<JobPosting> findJobPostingByName(String name) {
        return jobRepo.findJobPostingByJobNameOrderByDatePostedDesc(name);
    }
//    JobPosting job =  jobRepo.getById(id);

    public List<JobPosting> searchJobs(String searchQuery) {
        return jobRepo.searchJobs(searchQuery);
    }

    public void save(JobPosting jobPosting) {
        jobRepo.save(jobPosting);
    }
}
