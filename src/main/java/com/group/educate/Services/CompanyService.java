package com.group.educate.Services;

import com.group.educate.Model.User.Company.Recruiter;
import com.group.educate.Model.User.User;
import com.group.educate.Repo.BaseRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import com.group.educate.Model.User.Company.Company;

import java.util.List;

import static com.group.educate.Services.FilePaths.companyPath;
import static com.group.educate.Services.FilePaths.userPath;

@Service
public class CompanyService {
    protected final BaseRepository<User> repo = new BaseRepository<>(User.class, userPath);
    private final BaseRepository<Company> companyRepo = new BaseRepository<>(Company.class, companyPath);
    public RecruiterService recruiterService;
    CompanyService(RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    @EventListener
    public void handleRecruiterAddedEvent(RecruiterAddedEvent event) throws  Exception {
        String recruiterId = event.getRecruiterId();
        String companyId = event.getCompanyId();
        List<Company> companies= companyRepo.findAll();
        Recruiter recruiter =recruiterService.findRecruiterById(recruiterId);
        Company company = recruiterService.findCompanyById(companyId);
        company.addRecruiter(recruiter);
        companyRepo.saveAll(companies);
        System.out.println("Company updated after recruiter was added.");
    }
}

