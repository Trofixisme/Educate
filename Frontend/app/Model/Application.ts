import type {ApplicationStatus} from "~/Model/ApplicationStatus";
import type { JobPosting } from "./Jobs/JobPosting";
import type {Student} from "~/Model/Users/Student";

export class Application {

    id: bigint
    fname: string
    lname: string
    email: string
    phoneNumber: string
    applicationDate: Date
    jobPosting: JobPosting
    status: ApplicationStatus
    student: Student

    constructor(id: bigint, fname: string, lname: string, email: string, phoneNumber: string, applicationDate: Date, jobPosting: JobPosting, status: ApplicationStatus,student:Student) {
        this.id= id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;

        this.phoneNumber = phoneNumber;
        this.applicationDate = applicationDate;
        this.jobPosting = jobPosting;
        this.status=status;
        this.student=student;
    }
}