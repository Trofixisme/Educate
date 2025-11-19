package com.group.educate.Model.Roadmap.Skill;

import com.group.educate.Services.RoadmapProgression;
import com.group.educate.Model.Roadmap.Status;
import com.group.educate.Model.User.Student;

import java.time.Instant;
import java.util.Date;
//Need to rethink user skill status and roadmap progression relationship and how they work

@SuppressWarnings({"all"})
public class UserSkillStatus {

    private Skill skill;
    private Student student;

    private Status status;
    private Date lastModified = Date.from(Instant.now());

    protected RoadmapProgression roadmapProgressionDelegate;

    //MARK: Constructors
    public UserSkillStatus(Skill skill, Student student, Status status) {
        this.skill = skill;
        this.student = student;
        this.status = status;
    }

    public UserSkillStatus(Skill skill, Student student) {
        this(skill, student, Status.NOT_STARTED);
    }

    //MARK: Getters and Setters
    public Date getLastModified() { return lastModified; }

    public void setLastModified(Date newLastModifiedTime) {
        if (newLastModifiedTime.getTime() < lastModified.getTime()) {
         throw new RuntimeException("You cannot change the last modified date to a time that is before the current last modified date");
        } else {
            lastModified = newLastModifiedTime;
        }
    }

    public void setStatus(Status status) {
        this.status = status;
        if (roadmapProgressionDelegate != null) {
            roadmapProgressionDelegate.updateCompletionPercentage();
        }
    }

    public Status getStatus() { return status; }

    //MARK: Methods
    public void setDelegate(RoadmapProgression roadmapProgressionDelegate) {
        this.roadmapProgressionDelegate = roadmapProgressionDelegate;
    }
}
