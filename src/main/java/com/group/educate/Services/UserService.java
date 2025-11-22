package com.group.educate.Services;

import com.group.educate.Model.Roadmap.Roadmap;
import com.group.educate.Model.User.Student;
import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;
import com.group.educate.Repo.BaseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Ziad, Shimaa follow this structure for the rest of services
@Service
public class UserService {
    //todo:login, update: done!!
    //todo:register (make sure that user doesn't already exist), update: done!!
    //todo:view profile, done but review and test ziad. Done
    //todo:view roadmaps even if not signed in, just won't be able to track progress, done!

    protected final BaseRepository<User> repo = new BaseRepository<>(User.class, FilePaths.userPath);
    protected final BaseRepository<Roadmap> RoadmapRepo = new BaseRepository<>(Roadmap.class, FilePaths.roadmapPath);

    public void register(User u) throws Exception {
        List<User> users = repo.findAll();

        if (!users.contains(u)) {
            users.add(u);
            repo.saveAll(users);
        } else {
            throw new Exception("user already exists");
        }
    }

    public String login(String email, String password) throws Exception {
        if (email == null || password == null) { return "not found"; }

        List<User> users = repo.findAll();
        for (User u : users) {
            if (u.verifyPassword(password) && u.getEmail().equals(email)) {
                return "user found";
            } else if ((u.getEmail().equals(email)) && !u.verifyPassword(password)) {
                return "incorrect password";
            }
        }
        return "not found";
    }

    User viewProfile(String email) {
        try {
            List<User> users = repo.findAll();
            for (User u : users) {
                if(u.getEmail().equals(email)){
                    u.hashPassword("null");
                    return u;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Roadmap> viewRoadmaps() throws Exception {
        return RoadmapRepo.findAll();
    }

    private static final class testService {

        static void main () throws Exception {
            UserService service = new UserService();

            Student u1 = new Student("Ziad", "Ali", "ziad@example.com", "password123", UserRole.STUDENT, 2024, "Cairo University", "Computer Science", "FCAI");
//            service.register(u1);
            System.out.println(service.repo.findAll());
            System.out.println(service.viewProfile("ziad@example.com"));
            System.out.println(service.viewProfile("ziad2@example.com"));
        }
    }
}


