package com.group.InternMap.Model.Job;

import java.io.Serializable;
import java.lang.instrument.IllegalClassFormatException;

public class FreeLanceProject extends JobPosting implements Serializable {

    private double Duration;
    private int Payout;
    private String jobLocation;

    public FreeLanceProject() {
        super();
    }

    public FreeLanceProject( String jobDescription, String jobName, String jobLocation, String jobRequirements, String jobTitle, PostingType jobPostingType, double Duration, int Payout) {
        super(jobDescription,jobName, jobRequirements, jobTitle,jobPostingType);
        this.jobLocation = jobLocation;
        this.Duration = Duration;
        this.Payout = Payout;
    }

    public FreeLanceProject(String freeLanceProjectID , String jobDescription, String jobName, String jobLocation, String jobRequirements, String jobTitle, PostingType jobPostingType,int Duration, int Payout) {
        super(freeLanceProjectID ,jobDescription, jobRequirements, jobTitle,jobPostingType);
        this.Duration = Duration;
        this.Payout = Payout;
        this.jobLocation = jobLocation;
    }

    public double getDuration() {
        return Duration;
    }

    public int getPayout() {
        return Payout;
    }

    public String toString(){

        return super.toString()
                + Duration + '|'
                + Payout + '|'
                + jobLocation+'|';
    }

    private static final class FreeLancingTest {
        public static void main(String[] args) throws IllegalClassFormatException {
            FreeLanceProject project = new FreeLanceProject("Build a website", "Website Developer", "Remote", "HTML, CSS, JS", "Frontend Developer", PostingType.FreeLanceProject, 30, 1500);
            System.out.println(project.toString());

        }
    }
}
