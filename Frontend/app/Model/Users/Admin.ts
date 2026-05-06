class Admin extends User {

    PermissionLevel: PermissionLevel;

    constructor(email: String, fname: String, lname: String, role: String, id: bigint, createdAt: Date, PermissionLevel: PermissionLevel) {
        super(email, fname, lname, role, id, createdAt);
        this.PermissionLevel = PermissionLevel;
    }
}

enum PermissionLevel {

    LOW,
    MEDIUM,
    HIGH,
    COMPLETE_ACCESS
}