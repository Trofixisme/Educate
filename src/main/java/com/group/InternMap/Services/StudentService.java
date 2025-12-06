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
import com.group.InternMap.Repo.BaseRepository;

import static com.group.InternMap.Repo.RepositoryAccessors.allApplications;
import static com.group.InternMap.Repo.RepositoryAccessors.allJobPostings;

@Service
public class StudentService extends UserService implements FilePaths {
    public void deleteApplication(Student student, JobPosting jobPosting,Application application) throws Exception {
        if(allApplications.contains(application)){
            allApplications.remove(application);
            String appId = application.getApplicationID().toString();
            allJobPostings.stream()
                    .filter(j -> j.viewApplications().stream()
                            .anyMatch(a -> a.getApplicationID().equals(appId)))
                    .findFirst()
                    .ifPresent(j ->
                            j.viewApplications()
                                    .removeIf(a -> a.getApplicationID().equals(appId)));
            jobPosting.deleteApplication(application);
        }
    }

    public List<JobPosting> findJobposting() throws Exception{
        return allJobPostings;
    }

    public List<JobPosting> findJobpostingByname(String name) throws Exception{
        return RepositoryAccessors.allJobPostings.stream().filter(job -> job.getJobName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    public List<JobPosting> findJobpostingByType(PostingType type) throws Exception{
        return RepositoryAccessors.allJobPostings.stream().filter(job -> job.getJobPostingType().equals(type)).collect(Collectors.toList());
    }

}