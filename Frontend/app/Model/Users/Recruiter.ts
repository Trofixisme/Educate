class Recruiter extends User {

    title: string
    companies: Company[]

    constructor(email: String, fName: String, lName: String, role: String, id: bigint, dateCreated: Date, title: string, companies: Company[]) {
        super(email, fName, lName, role, id, dateCreated);
        this.title = title;
        this.companies = companies;
    }
}