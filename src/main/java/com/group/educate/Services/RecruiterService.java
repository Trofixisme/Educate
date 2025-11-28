package com.group.educate.Services;

import com.group.educate.Model.Job.JobPosting;
import com.group.educate.Model.User.Application;
import com.group.educate.Model.User.Company.Company;
import com.group.educate.Model.User.Company.Recruiter;
import com.group.educate.Model.User.User;
import com.group.educate.Repo.BaseRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecruiterService extends UserService {
    //todo:need to create job postings
    //todo:need to view applications
    //todo:need to search for applications
    //todo:need to filter applications
    //todo:add company

    private final BaseRepository<JobPosting> jobRepo = new BaseRepository<>(JobPosting.class, jobPostingPath);
    private final BaseRepository<Application> applicationRepo = new BaseRepository<>(Application.class, applicationPath);

    private final BaseRepository<Company> companyRepo = new BaseRepository<>(Company.class, companyPath);
    private final ApplicationEventPublisher eventPublisher;
    public RecruiterService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
//    public void addRecruiterToCompany(Recruiter recruiter, Company company) {
//        // Main business logic (e.g., save recruiter to DB)
//        // recruiterRepository.save(recruiter);
//
//        // Publish the event to notify other components
//        eventPublisher.publishEvent(new RecruiterAddedEvent(recruiter, company));
//    }
//}

      public void register(User u, Company company) throws Exception {
        List<User> users = repo.findAll();

        if (!users.contains(u)) {
            users.add(u);
            repo.saveAll(users);

            eventPublisher.publishEvent(new RecruiterAddedEvent(u.getUserID(), company.getCompanyID()));
        } else {
            throw new Exception("user already exists");
        }
    }
     public Recruiter findRecruiterById(String recruiterId) throws Exception {
        if(recruiterId == null || recruiterId.isBlank()) {
            throw new IllegalArgumentException("recruiterId must be provided");
        }
        return (Recruiter) repo.findAll().stream()
                .filter(u -> u.getUserID().equals(recruiterId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));
    }

    public Company findCompanyById(String companyId) throws Exception {
        if (companyId == null || companyId.isBlank()) {
            throw new IllegalArgumentException("companyId must be provided");
        }

        return companyRepo.findAll().stream()
                .filter(c -> java.util.Objects.equals(c.getCompanyID(), companyId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Company not found: " + companyId));
    }


    public void addCompanyToRecruiter(String recruiterId, String companyId) throws Exception {
        List<User> users = repo.findAll();
        Recruiter recruiter =findRecruiterById(recruiterId);
        Company company = findCompanyById(companyId);
        recruiter.addCompany(company);
        repo.saveAll( users);
        eventPublisher.publishEvent(new RecruiterAddedEvent(recruiter.getUserID(), company.getCompanyID()));
    }

    public void addJobPosting(JobPosting jobPosting) throws Exception {
        List<JobPosting> jobPostings = jobRepo.findAll();
        jobPostings.add(jobPosting);
        jobRepo.saveAll(jobPostings);
    }

    public void addCompany(Company company) throws Exception {
        List<Company> companies = companyRepo.findAll();
        if (companies.contains(company)) {
            throw new Exception("Company already exists");
        }
        companies.add(company);
        companyRepo.saveAll(companies);
    }


    public List<Company> viewAllCompanies() throws Exception {
        return companyRepo.findAll();
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
