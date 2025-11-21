package com.group.educate.Services;

import com.group.educate.Model.Roadmap.Skill.Skill;
import com.group.educate.Model.User.Student;
import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;
import com.group.educate.Repo.BaseRepository;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.group.educate.Model.User.UserRole.STUDENT;
//ziad,shimaa follow this structure for the rest of services
@Service
public class UserService {
    private static final String fileName = "data/users.txt";
    //todo:login
//todo:register (make sure that user doesn't already exist) ,update done!!
//todo:view profile
//todo:view roadmaps even if not signed in, just won't be able to track progress
    private final BaseRepository<User> repo =
            new BaseRepository<>(User.class, fileName);

    public void register(User u) throws Exception {
        List<User> users = repo.findAll();
        users.add(u);
        repo.saveAll(users);
    }
    void main()throws Exception{
        Student u1= new Student("Ziad", "Ali", "ziad@example.com", "password123", UserRole.STUDENT, 2024, "Cairo University", "Computer Science", "FCAI");
        register(u1);
        System.out.println(repo.findAll());
    }

}


