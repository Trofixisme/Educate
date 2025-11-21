package com.group.educate.Services;

import com.group.educate.Model.Roadmap.Roadmap;
import com.group.educate.Model.Roadmap.Skill.Skill;
import com.group.educate.Model.User.Student;
import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;
import com.group.educate.Repo.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.group.educate.Model.User.UserRole.STUDENT;
//ziad,shimaa follow this structure for the rest of services
@Service
public class UserService {
    private static final String fileName = "data/users.txt";
    //todo:login,update done!!
//todo:register (make sure that user doesn't already exist) ,update done!!
//todo:view profile ,done but review and test ziad
//todo:view roadmaps even if not signed in, just won't be able to track progress,done!
    protected final BaseRepository<User> repo =
            new BaseRepository<>(User.class, fileName);
    protected final BaseRepository<Roadmap> Roadmaprepo =
            new BaseRepository<>(Roadmap.class, fileName);

    public void register(User u) throws Exception {
        List<User> users = repo.findAll();
        users.add(u);
        repo.saveAll(users);
    }
    public  String login(String email,String password)throws Exception{
        if(email==null || password==null){
            return "not found";
        }
        List<User> users=repo.findAll();
        for(User u:users){
             if (u.getHashedPassword().equals(u.hashPassword(password))){
                return "user found";
            }
             else  if((u.getEmail().equals(email))&&!u.getHashedPassword().equals(u.hashPassword(password))){
                 return "incorrrect password";
             }
        }
        return "not found";

    }
    User viewProfile(String email)
    {
        try{
            List<User> users=repo.findAll();
            for(User u:users){
                if(u.getEmail().equals(email)){
                    return u;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();

        }
        return null;

    }
    List<Roadmap> viewRoadmaps() throws Exception {
        return Roadmaprepo.findAll();
    }


    void main()throws Exception{
        Student u1= new Student("Ziad", "Ali", "ziad@example.com", "password123", UserRole.STUDENT, 2024, "Cairo University", "Computer Science", "FCAI");
        register(u1);
        System.out.println(repo.findAll());
    }

}


