import type {ApplicationStatus} from "~/Model/ApplicationStatus";

export class Application {

    id: bigint
    fName: string
    lName: string
    email: string
    phoneNumber: string
    applicationDate: Date
    jobPosting: JobPosting
    status: ApplicationStatus
    student:Student

    constructor(id: bigint, fName: string, lName: string, email: string, phoneNumber: string, applicationDate: Date, jobPosting: JobPosting, status: ApplicationStatus,student:Student) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.applicationDate = applicationDate;
        this.jobPosting = jobPosting;
        this.status=status;
        this.student=student;
    }
}