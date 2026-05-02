class User {

    email: String;
    fName: String;
    lName: String;
    role: String;
    id: bigint;
    createdAt: Date

    constructor(email: String, fName: String, lName: String, role: String, id: bigint, createdAt: Date) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.role = role;
        this.id = id;
        this.createdAt = createdAt;
    }
}