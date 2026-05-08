export class User {

    email: String;
    fname: String;
    lname: String;
    role: String;
    id: bigint;
    createdAt: Date;
    profilePicture:String;

    constructor(email: String, fname: String, lname: String, role: String, id: bigint, createdAt: Date,profilePicture:String) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.role = role;
        this.id = id;
        this.createdAt = createdAt;
        this.profilePicture=profilePicture;
    }
}