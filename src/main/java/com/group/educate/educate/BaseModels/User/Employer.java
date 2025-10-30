//Created by Ziad on 28/10/2025

package com.group.educate.educate.BaseModels.User;

import java.util.concurrent.atomic.AtomicInteger;

public class Employer extends User {

    static AtomicInteger userCounter = new AtomicInteger();

    public Employer(String password, String name, String email) {
        super(password, name, email, UserStatics.employer);
    }
}
