//Made By Eyad

package com.group.educate.educate.Model.Job;

public class FullTime extends JobPosting{
    private String Benefits;

    public FullTime(Type JobType) {
        super(JobType);
        this.Benefits = "";
    }

    public String getBenefits() {
        return Benefits;
    }
    public void setBenefits(String Benefits) {
        this.Benefits = Benefits;
    }
}
