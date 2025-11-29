package com.group.InternMap.Services;

import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.User.Company.Recruiter;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Repo.BaseRepository;
import org.springframework.stereotype.Service;
import java.util.*;

import static com.group.InternMap.Repo.RepositoryAccessors.allJobPostings;
import static com.group.InternMap.Services.FilePaths.jobPostingPath;
import static com.group.InternMap.Services.FilePaths.userPath;

@Service
public class JobPostingService {

    private final BaseRepository<JobPosting> jobRepo = new BaseRepository<>(JobPosting.class, jobPostingPath);
    private final BaseRepository<User> userRepo = new BaseRepository<>(User.class, userPath);
    //find by company name
    public List<JobPosting> findByCompanyName(String companyName) throws Exception {
        return jobRepo.search( jobPosting -> jobPosting.getCompanyName().equalsIgnoreCase(companyName));
    }
    public List<JobPosting> findJobPostingsByRecruiterEmail(String email) throws Exception {
        return jobRepo.search(job -> job.getRecruiterEmail().equalsIgnoreCase(email));
    }




//    JobPosting SearchbySeany(String companyName){
//        for(JobPosting jobPosting : allJobPostings){
//            if(jobPosting.getCompanyName().equals(companyName)){
//                return  jobPosting;
//            }
//        }
//        return null;
//    }
//}
//JobPosting SearchbyRecruiter(String companyName){
//    for(JobPosting jobPosting : allJobPostings){
//        if(jobPosting.getCompanyName().equals(companyName)){
//            return  jobPosting;
//        }
//    }
//    return null;
}


