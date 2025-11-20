package com.group.educate.Services;

import com.group.educate.Model.User.UserRole;
import com.group.educate.Repo.BaseRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    //login
    //register (make sure that user doesn't already exist)
    //view profile
    //view roadmaps even if not signed in,just won't be able to track progress
BaseRepository baseRepository;
UserService(BaseRepository baseRepository){
this.baseRepository=baseRepository;
}

}
