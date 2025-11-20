package com.group.educate.Services;

import com.group.educate.Repo.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    //needed to save the application
    BaseRepository baseRepository;

    public ApplicationService(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }
}
