package com.group.educate.Services;
import com.group.educate.Model.Job.JobPosting;
import com.group.educate.Model.User.Application;
import com.group.educate.Model.User.Company.Company;
import com.group.educate.Repo.BaseRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecruiterService extends UserService{
    //todo:need to create job postings
    //todo:need to view applications
    //todo:need to search for applications
    //todo:need to filter applications
    //todo:add company
    private static final String fileNameJob = "data/jobs.txt";
    private static final String fileNameJApplication = "data/applications.txt";
    private static final String fileNameCompany= "data/companies.txt";
    private final BaseRepository<JobPosting> jobRepo = new BaseRepository<>(JobPosting.class, fileNameJob);
    private final BaseRepository<Application> applicationRepo = new BaseRepository<>(Application.class, fileNameJApplication);
    private final BaseRepository<Company> companyRepo = new BaseRepository<>(Company.class, fileNameCompany);

    public void addJobPosting(JobPosting jobPosting) throws Exception {
        List<JobPosting> jobPostings= jobRepo.findAll();
        jobPostings.add(jobPosting);
        jobRepo.saveAll(jobPostings);
    }

    public void addCompany(Company company) throws Exception {
        List<Company> companies= companyRepo.findAll();
        companies.add(company);
        companyRepo.saveAll(companies);
    }

    public List<Application> viewAllApplications() throws Exception {
        return applicationRepo.findAll();
    }
    public Application searchApplication(UUID appId) throws Exception {
        List<Application> applications = applicationRepo.findAll();
        return applications.stream().filter(app -> app.getApplicationID().equals(appId)).findFirst().orElse(null);
    }
    public List<Application> filterApplication(UUID appId) throws Exception {
        List<Application> applications = applicationRepo.findAll();
        return applications.stream().filter(app -> app.getApplicationID().equals(appId)).collect(Collectors.toList());
    }

}
