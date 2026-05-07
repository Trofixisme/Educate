class RecruiterRegistrationDTO {

    user: Recruiter;
    company: Company;

    constructor(recruiter: user, company: Company) {
        this.user = recruiter;
        this.company = company;
    }
}