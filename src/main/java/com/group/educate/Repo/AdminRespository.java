package com.group.educate.Repo;

import com.group.educate.Model.User.Admin;
import com.group.educate.Model.User.User;
import com.group.educate.Model.User.UserRole;

public class AdminRespository extends BaseRepository {

    public AdminRespository(String path) {
        super(path);
    }

    @Override
    User parseObject(String line) {
        String[] parts=line.split("\\|");

        return new Admin(parts[1],parts[2], parts[3], parts[4], UserRole.ADMIN);
    }
}
