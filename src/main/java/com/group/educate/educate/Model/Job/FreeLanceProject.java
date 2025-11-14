//Made By Eyad

package com.group.educate.educate.Model.Job;

import java.lang.instrument.IllegalClassFormatException;

public class FreeLanceProject extends JobPosting{

    private int Duration;
    private int Payout;



    public FreeLanceProject(String JobDescription, String JobName, String JobLocation, String JobRequirements, String JobTitle, PostingType jobPostingType,int Duration,
   int Payout) {
        super(JobDescription, JobName, JobLocation,JobRequirements, JobTitle,jobPostingType);
        this.Duration = Duration;
        this.Payout = Payout;
    }

    public int getDuration() {
        return Duration;
    }

    public int getPayout() {
        return Payout;
    }
    // Access JobPosting id via getter
    public int getPostingId() {
        return super. getJobPostingID();
    }

    public String toString(){
        return "FreeLanceProject{" +
                "Duration=" + Duration +
                ", Payout=" + Payout +
                ", JobPostingID=" + getJobPostingID() +
                '}';
    }
    public static void main(String[] args) throws IllegalClassFormatException {
        FreeLanceProject project = new FreeLanceProject("Build a website", "Web Developer", "Remote", "HTML, CSS, JS", "Website Project", PostingType.FreeLanceProject,1,15);
        FreeLanceProject project2 = new FreeLanceProject("Build a website", "Web Developer", "Remote", "HTML, CSS, JS", "Website Project", PostingType.FreeLanceProject,1,15);
        System.out.println(project.toString());
        System.out.println(project2.toString());
    }

}
