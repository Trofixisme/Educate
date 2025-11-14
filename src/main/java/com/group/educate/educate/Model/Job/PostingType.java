//Made By Eyad

package com.group.educate.educate.Model.Job;

public enum PostingType {

    Internship(1),
    FullTime(2),
    FreeLanceProject(3);

    private final int id;

    PostingType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
