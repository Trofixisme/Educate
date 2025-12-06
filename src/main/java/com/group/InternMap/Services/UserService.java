package com.group.InternMap.Services;

import com.group.InternMap.Model.Roadmap.Roadmap;
import com.group.InternMap.Model.User.Student;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Model.User.UserRole;
import com.group.InternMap.Repo.BaseRepository;
import com.group.InternMap.Repo.RepositoryAccessors;
import org.springframework.stereotype.Service;

import java.util.List;

//Ziad, Shimaa follow this structure for the rest of services
@Service
public class UserService implements FilePaths {
    protected final BaseRepository<User> repo = new BaseRepository<>(User.class, userPath);
    protected final BaseRepository<Roadmap> RoadmapRepo = new BaseRepository<>(Roadmap.class, roadmapPath);

    public void register(User u) throws Exception {
        List<User> users = RepositoryAccessors.allUsers;

        if (!users.contains(u)) {
            users.add(u);
        } else {
            throw new Exception("user already exists");
        }
    }

    public User login(String email, String password) throws Exception {

        if (email == null || password == null) {
            throw new IllegalArgumentException("Email and password cannot be null");
        }

        List<User> users = RepositoryAccessors.allUsers;

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
                if (u.getEmail().equals(email)) {
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

}


