package com.group.educate.educate.Model.Roadmap;
import com.group.educate.educate.Model.Job.PostingType;
import com.group.educate.educate.Model.Roadmap.Skill.Skill;
import com.group.educate.educate.Model.Roadmap.Skill.UserSkillStatus;
import com.group.educate.educate.Model.User.Student.Student;
import com.group.educate.educate.Model.User.Student.StudentDepartment;
import com.group.educate.educate.Model.User.Student.StudentMajor;
import com.group.educate.educate.Model.User.UserRole;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings({"all"})
public class RoadmapProgression {
    private static int skillcounter = 0;
    private final int RoadmapProgressionId;
    private double percentage;
    private Date lastUpdated = Date.from(Instant.now());
    private Roadmap roadmap;
    private ArrayList<UserSkillStatus> userSkillStatuses;
    private double currentCompletionPercentage;
    private int completeModulesCount;
    private int incompleteModulesCount;

    public RoadmapProgression(Roadmap roadmap) {
        this.RoadmapProgressionId = ++skillcounter;
        this.roadmap = roadmap;
        this.userSkillStatuses = new ArrayList<>();
    }

    public void addUserSkillStatus(UserSkillStatus userSkillStatus) {
        userSkillStatuses.add(userSkillStatus);
        updateCompletionPercentage();
        updateLastUpdated();
    }

    public void updateUserSkillStatus(UserSkillStatus userSkillStatus) {
        int index = userSkillStatuses.indexOf(userSkillStatus);
        if (index != -1) {
            userSkillStatuses.set(index, userSkillStatus);
            updateCompletionPercentage();
            updateLastUpdated();
        }
    }

    private void updateCompletionPercentage() {
        if (userSkillStatuses.isEmpty()) {
            percentage = 0;
            completeModulesCount = 0;
            incompleteModulesCount = 0;
            return;
        }

        int doneCount = 0;
        for (UserSkillStatus status : userSkillStatuses) {
            if (status.getStatus() == UserSkillStatus.Status.DONE||status.getStatus() == UserSkillStatus.Status.SKIP) {
                doneCount++;
            }
        }

        completeModulesCount = doneCount;
        incompleteModulesCount = userSkillStatuses.size() - doneCount;
        percentage = ((double) doneCount / userSkillStatuses.size()) * 100;
        currentCompletionPercentage = percentage;
    }

    private void updateLastUpdated() {
        lastUpdated = Date.from(Instant.now());
    }

    public int getRoadmapProgressionId() {
        return RoadmapProgressionId;
    }

    public double getPercentage() {
        return percentage;
    }

    public int getCompleteModulesCount() {
        return completeModulesCount;
    }

    public int getIncompleteModulesCount() {
        return incompleteModulesCount;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public static void main(String[] args) {
        // Create a test roadmap with mock ID
        RoadmapModule R1=new RoadmapModule("front end","learn front end");
        Roadmap testRoadmap = new Roadmap("Java Fundamentals",R1);
        RoadmapProgression progression = new RoadmapProgression(testRoadmap);

        // Create test skills with mock data
        Skill skill1 = new Skill("java basics");
        Skill skill2 = new Skill("oop concepts");
        StudentMajor m1=new StudentMajor("213","Computer Science");
        StudentDepartment sp=new StudentDepartment("213","cs");
        // Create student with mock data
        //PostingType.FreeLanceProject
        Student student = new Student("John ", "Doe", "john@example.com","....", UserRole.STUDENT,2026,m1,sp,",,");

        // Add user skill statuses
        UserSkillStatus uss1 = new UserSkillStatus(skill1,student, UserSkillStatus.Status.DONE);
        UserSkillStatus uss2 = new UserSkillStatus(skill2,student, UserSkillStatus.Status.IN_PROGRESS);

        progression.addUserSkillStatus(uss1);
        progression.addUserSkillStatus(uss2);

        // Print results
        System.out.println("=== Roadmap Progression Test ===");
        System.out.println("Total Skills: " + (progression.getCompleteModulesCount() + progression.getIncompleteModulesCount()));
        System.out.println("Complete: " + progression.getCompleteModulesCount());
        System.out.println("Incomplete: " + progression.getIncompleteModulesCount());
        System.out.println("Completion %: " + String.format("%.2f", progression.getPercentage()) + "%");
        System.out.println("Last Updated: " + progression.getLastUpdated());
    }

}