package com.group.educate.Services;

import com.group.educate.Model.Roadmap.Roadmap;
import com.group.educate.Model.Roadmap.Skill.UserSkillStatus;
import com.group.educate.Model.Roadmap.Status;
//import com.group.educate.Model.User.Student.StudentMajor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@SuppressWarnings({"all"})
public class RoadmapProgression {

    private UUID uuid;

    private double percentage;
    private Date lastModified = Date.from(Instant.now());

    private Roadmap roadmap;
    private ArrayList<UserSkillStatus> userSkillStatuses = new ArrayList<>();

    private double currentCompletionPercentage;
    private int numberOfCompleteModules;
    private int numberOfIncompleteModules;

    public RoadmapProgression(Roadmap roadmap) {
        uuid = UUID.randomUUID();
        this.roadmap = roadmap;
    }

    public void addUserSkillStatus(UserSkillStatus userSkillStatus) {
        userSkillStatus.setDelegate(this);
        userSkillStatuses.add(userSkillStatus);
        updateCompletionPercentage();
        updateLastModified();
    }

    public void updateCompletionPercentage() {
        if (userSkillStatuses.isEmpty()) {
            percentage = 0;
            numberOfCompleteModules = 0;
            numberOfIncompleteModules = 0;
            return;
        }

        int CompleteCount = 0;
        for (UserSkillStatus status : userSkillStatuses) {
            if (status.getStatus() == Status.DONE || status.getStatus() == Status.SKIPPED) {
                CompleteCount++;
            }
        }

        numberOfCompleteModules = CompleteCount;
        numberOfIncompleteModules = userSkillStatuses.size() - CompleteCount;
        percentage = ((double) CompleteCount / userSkillStatuses.size()) * 100;
        currentCompletionPercentage = percentage;
    }

    private void updateLastModified() {
        lastModified = Date.from(Instant.now());
    }

    public String getRoadmapProgressionID() {
        return uuid.toString();
    }

    public double getPercentage() {
        return percentage;
    }

    public int getNumberOfCompleteModules() {
        return numberOfCompleteModules;
    }

    public int getNumberOfIncompleteModules() {
        return numberOfIncompleteModules;
    }

    public Date getLastModified() {
        return lastModified;
    }

//    private class RoadmapTest {
//        public static void main(String[] args) {
//            // Create a test roadmap with mock ID
//            RoadmapModule R1 = new RoadmapModule("front end", "learn front end");
//            Roadmap testRoadmap = new Roadmap("Java Fundamentals", R1);
//            RoadmapProgression progression = new RoadmapProgression(testRoadmap);
//
//            // Create test skills with mock data
//            Skill skill1 = new Skill("java basics");
//            Skill skill2 = new Skill("oop concepts");
////            StudentMajor m1 = new StudentMajor( "Computer Science");
//            StudentDepartment sp = new StudentDepartment( "cs");
//            // Create student with mock data
//            //PostingType.FreeLanceProject
////            Student student = new Student("John ", "Doe", "john@example.com", "....", UserRole.STUDENT, 2026, m1, sp);
//
//            // Add user skill statuses
////            UserSkillStatus uss1 = new UserSkillStatus(skill1, student, Status.DONE);
////            UserSkillStatus uss2 = new UserSkillStatus(skill2, student, Status.IN_PROGRESS);
////
////            progression.addUserSkillStatus(uss1);
////            progression.addUserSkillStatus(uss2);
//
//            // Print results
//            System.out.println("=== Roadmap Progression Test ===");
//            System.out.println("Total Skills: " + (progression.getNumberOfCompleteModules() + progression.getNumberOfIncompleteModules()));
//            System.out.println("Complete: " + progression.getNumberOfCompleteModules());
//            System.out.println("Incomplete: " + progression.getNumberOfIncompleteModules());
//            System.out.println("Completion %: " + String.format("%.2f", progression.getPercentage()) + "%");
//            System.out.println("Last Updated: " + progression.getLastModified());
//        }
//    }
}