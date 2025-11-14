//Made By Eyad

public class FreeLanceProjectInfo extends JobPosting{
    private int Duration;
    private int Payout;


    public FreeLanceProjectInfo(Type JobType){
        super(JobType);
        this.Duration = 0;
        this.Payout = 0;
    }

    public int getDuration() {
        return Duration;
    }

    public int getPayout() {
        return Payout;
    }
}
