package com.group.InternMap.Application;

import java.io.Serializable;

public enum ApplicationStatus implements Serializable {

    ACCEPTED(1),
    REJECTED(2),
    PENDING(3);

    private final int id;

    ApplicationStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
