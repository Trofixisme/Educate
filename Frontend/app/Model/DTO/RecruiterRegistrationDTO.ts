import type { Recruiter } from "../Users/Recruiter";
import { Company } from "../Company";

export class RecruiterRegistrationDTO {

    user: Recruiter;
    company: Company;

    constructor(user: Recruiter, company: Company) {
        this.user = user;
        this.company = company;
    }
}