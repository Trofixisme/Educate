package com.group.educate.Services;

import com.group.educate.Repo.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends UserService{
    //view all users
    //delete users
    //create roadmaps
    //delete roadmaps
    //update roadmaps
    //view all job postings
    //delete job posting
    BaseRepository baseRepository;
    AdminService(BaseRepository baseRepository){
       super(baseRepository);
    }

}
