package com.group.InternMap.Services;

import com.group.InternMap.Model.User.Company.Recruiter;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Repo.BaseRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import com.group.InternMap.Model.User.Company.Company;

import java.util.List;

import static com.group.InternMap.Repo.RepositoryAccessors.allCompanies;
import static com.group.InternMap.Services.FilePaths.companyPath;
import static com.group.InternMap.Services.FilePaths.userPath;

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
        Recruiter recruiter =recruiterService.findRecruiterById(recruiterId);
        Company company = recruiterService.findCompanyById(companyId);
        company.addRecruiter(recruiter);
        System.out.println("Company updated after recruiter was added.");
    }
    public static Company findByName(String companyName) {
        if (companyName == null || companyName.isBlank()) return null;

        return allCompanies.stream()
                .filter(c -> c.getName().equalsIgnoreCase(companyName))
                .findFirst()
                .orElse(null);
    }


}

