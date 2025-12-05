package com.group.InternMap.Services;

import com.group.InternMap.Model.Job.JobPosting;
import com.group.InternMap.Model.User.Application;
import com.group.InternMap.Model.User.Company.Company;
import com.group.InternMap.Model.User.Company.Recruiter;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Repo.BaseRepository;
import com.group.InternMap.Repo.RepositoryAccessors;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.group.InternMap.Repo.RepositoryAccessors.*;

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

    public Recruiter findRecruiterById(String recruiterId) {
        if (recruiterId == null || recruiterId.isBlank()) {
            throw new IllegalArgumentException("recruiterId must be provided");
        }

        return RepositoryAccessors.allUsers.stream()
                .filter(u -> u instanceof Recruiter)
                .map(u -> (Recruiter) u)
                .filter(r -> r.getUserID().equals(recruiterId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Recruiter not found: " + recruiterId));
    }

    public Company findCompanyById(String companyId) {
        if (companyId == null || companyId.isBlank()) {
            throw new IllegalArgumentException("companyId must be provided");
        }

        // Debug: print all companies
        RepositoryAccessors.allCompanies.forEach(c -> {
            System.out.println("Company ID: " + c.getCompanyID() + ", Name: " + c.getName());
        });

        for (Company c : RepositoryAccessors.allCompanies) {
            System.out.println("Checking Company: " + c);
        }

        System.out.println("======================");
        System.out.println("Company ID: " + companyId);


        return RepositoryAccessors.allCompanies.stream()
                .filter(c -> c.getCompanyID().toString().equals(companyId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Company not found: " + companyId));
    }

    public void addCompanyToRecruiter(String recruiterId, String companyId) throws Exception {

        Recruiter recruiter = findRecruiterById(recruiterId);
        Company company = CompanyService.findByName(companyId);
//        if (!allCompanies.contains(compan
//            allCompanies.add(company);
//        }
        recruiter.addCompany(company);
        eventPublisher.publishEvent(new RecruiterAddedEvent(recruiter.getUserID(), company.getCompanyID().toString()));
    }

//    public void checkCompany(Company company) throws Exception {
//        if (allCompanies.contains(company)) {
//         recruiter.addCompany(company);
//        }
//        allCompanies.add(company);
//    }

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
