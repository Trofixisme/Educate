//Made By Eyad

public class Application{
    private int ApplicationID;

    Type JobType = new Type("", 0);


    public Application(int applicationID, Type JobType){
        this.ApplicationID = applicationID;
        this.JobType = JobType;
    }

    public int getApplicationID(){
       return ApplicationID;
    }
}
