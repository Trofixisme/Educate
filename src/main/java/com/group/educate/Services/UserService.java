package com.group.educate.Services;

import com.group.educate.Model.Roadmap.Roadmap;
import com.group.educate.Model.User.Student;
import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;
import com.group.educate.Repo.BaseRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

//Ziad, Shimaa follow this structure for the rest of services
@Service
public class UserService implements FilePaths {
    //todo:login, update: done!!
    //todo:register (make sure that user doesn't already exist), update: done!!
    //todo:view profile, done but review and test ziad. Done
    //todo:view roadmaps even if not signed in, just won't be able to track progress, done!

    protected final BaseRepository<User> repo = new BaseRepository<>(User.class, userPath);
    protected final BaseRepository<Roadmap> RoadmapRepo = new BaseRepository<>(Roadmap.class, roadmapPath);

    public void register(User u) throws Exception {
        List<User> users = repo.findAll();

        if (!users.contains(u)) {
            users.add(u);
            repo.saveAll(users);
        } else {
            throw new Exception("user already exists");
        }
    }

    public User login(String email, String password) throws Exception {

        if (email == null || password == null) {
            throw new IllegalArgumentException("Email and password cannot be null");
        }

        List<User> users = repo.findAll();

        for (User u : users) {

            if (u.getEmail().equals(email)) {

                if (u.getPlainPassword().equals(password)) {
                    return u;
                }
                else if(!u.getPlainPassword().equals(password)) {
                    throw new Exception("incorrect password");
                }
            }
        }

        throw new Exception("user not found");
    }


    public User searchByEmail(String email) {
        try {
            List<User> users = repo.findAll();
            for (User u : users) {
                if(u.getEmail().equals(email)){
                    return u;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public User SearchbyID(String id) {
        try {
            List<User> users = repo.findAll();
            for (User u : users) {
                if (u.getUserID().toString().equals(id)) {
                    return u;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Roadmap> viewRoadmaps() throws Exception {
        return RoadmapRepo.findAll();
    }

    private static final class testService {

        static void main () throws Exception {
            UserService service = new UserService();

            Student u1 = new Student("Ziad", "Ali", "ziad2@example.com", "password123ashdashdoi", UserRole.STUDENT, 2024, "Cairo University", "Computer Science", "FCAI");
//            service.register(u1);
            System.out.println(service.repo.findAll());
            System.out.println(service.searchByEmail("ziad@example.com"));
            System.out.println(service.searchByEmail("ziad2@example.com"));

        }
    }
}


