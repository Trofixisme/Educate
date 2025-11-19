package com.group.educate.Repo;

import com.group.educate.Model.User.Company.Recruiter;
import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;

public class RecruiterRepository extends BaseRepository {

    public RecruiterRepository() {
        super("data/Users.txt");
    }

    User parseObject(String line) {
        String[] parts = line.split("\\|", -1);
        return new Recruiter(parts[0], parts[1], parts[2], parts[3], parts[4], UserRole.EMPLOYER, parts[6]);
    }

    private static final class RepositoryTest {

        static void main() {
            var repository = new RecruiterRepository();
            var recruiter = new Recruiter("John", "Doe", "provide@gmail.com", "123456", UserRole.EMPLOYER, "Software Engineer");
            System.out.println(recruiter);
            repository.save(recruiter);
            System.out.println(repository.findAll());
        }
    }
}
