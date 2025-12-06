package com.group.InternMap.Services;

import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.User.Company.Recruiter;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Repo.BaseRepository;
import com.group.InternMap.Repo.RepositoryAccessors;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

import static com.group.InternMap.Repo.RepositoryAccessors.allJobPostings;
import static com.group.InternMap.Services.FilePaths.jobPostingPath;
import static com.group.InternMap.Services.FilePaths.userPath;

@Service
public class JobPostingService {

    private final BaseRepository<JobPosting> jobRepo = new BaseRepository<>(JobPosting.class, jobPostingPath);
    //find by company name
//    public List<JobPosting> findByCompanyName(String companyName) throws Exception {
//        return jobRepo.search( jobPosting -> jobPosting.getCompanyName().equalsIgnoreCase(companyName));
//    }
//    public List<JobPosting> findJobPostingsByRecruiterEmail(String email) throws Exception {
//        return jobRepo.search(job -> job.getRecruiterEmail().equalsIgnoreCase(email));
//    }
    public List<JobPosting> searchJobPostings(String companyName, String recruiterEmail) throws Exception {
        return jobRepo.search(job -> {
            boolean matches = false;

            if (companyName != null && !companyName.isBlank()) {
                matches |= job.getCompanyName().equalsIgnoreCase(companyName);
            }

            if (recruiterEmail != null && !recruiterEmail.isBlank()) {
                matches |= job.getRecruiter().getEmail().equalsIgnoreCase(recruiterEmail);
            }

            return matches;
        });
    }
    public List<JobPosting> getAllJobPostings() throws Exception {
      return  RepositoryAccessors.allJobPostings;
    }
    public JobPosting findByID(UUID jopPostingId) {
        System.out.println(jopPostingId);

         return allJobPostings.stream()
                 .filter(j -> j.getJobPostingUUID().equals(jopPostingId))
                 .findFirst().orElse(null);
    }
//    public List<JobPosting> getJobPostingsByRecruiterId(UUID recruiterId) throws Exception {
//        if(recruiterId == null){
//            new Exception("recruiterId is null");
//        }
//        return allJobPostings.stream()
//                .filter(jobPosting -> jobPosting.getRecruiter().getUserID().equals(recruiterId))
//                        .collect(Collectors.toList());
//
//    }
public List<JobPosting> getJobPostingsByRecruiterId(UUID recruiterId) throws Exception {

    System.out.println("Looking for jobs for recruiter: " + recruiterId);

    return allJobPostings.stream()
            .peek(job -> System.out.println("Job Recruiter: " +
                    (job.getRecruiter() == null ? "null" : job.getRecruiter().getUserID())))
            .filter(job -> job.getRecruiter() != null)
            .filter(job -> recruiterId.equals(job.getRecruiter().getUserID()))
            .collect(Collectors.toList());
}






}
