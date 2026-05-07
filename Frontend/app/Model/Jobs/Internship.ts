import type { Company } from "../Company";
import type { Recruiter } from "../Users/Recruiter";
import { JobPosting } from "./JobPosting";

class Internship extends JobPosting {

    duration: number;
    jobLocation: String;

    constructor(id: bigint, jobDescription: String, jobRequirements: String, jobName: String, company: Company, jobLocation: String, postingDate: Date, recruiter: Recruiter, duration: number) {
        super(id, jobDescription, jobRequirements, jobName, company, jobLocation, postingDate, recruiter, "Internship");
        this.duration = duration;
        this.jobLocation = jobLocation;
    }
}