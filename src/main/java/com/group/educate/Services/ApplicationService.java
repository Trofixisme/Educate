package com.group.educate.Services;

import com.group.educate.Repo.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
    //todo:needed to save the application
    //you can suggest more feature if needed
    BaseRepository baseRepository;

    public ApplicationService(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }
}
