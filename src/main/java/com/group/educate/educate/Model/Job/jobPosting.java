//Made By Eyad

public class JobPosting {
    private String JobDescription;
    private String JobName;
    private String DatePosted;
    private String JobLocation;
    private String JobRequirements;
    private String JobTitle;
    private int JobPostingID;
    private Type JobType;


    public JobPosting(Type JobType){
        this.JobDescription = "";
        this.JobName = "";
        this.DatePosted = "";
        this.JobLocation = "";
        this.JobRequirements = "";
        this.JobTitle = "";
        this.JobPostingID = 0;
        this.JobType =JobType;
    }


    public Application Application (Application App1){
        return App1;
    }


    public String getJobLocation() {
        return JobLocation;
    }

    public void setJobLocation(String jobLocation) {
        JobLocation = jobLocation;
    }

    public String getDatePosted() {
        return DatePosted;
    }

    public void setDatePosted(String datePosted) {
        DatePosted = datePosted;
    }

    public String getJobDescription() {
        return JobDescription;
    }

    public String getJobName() {
        return JobName;
    }

    public String getJobRequirements() {
        return JobRequirements;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public int getJobPostingID() {
        return JobPostingID;
    }

    public Type getJobType() {
        return JobType;
    }
}
