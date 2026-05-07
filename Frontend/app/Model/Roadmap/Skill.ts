export class Skill {

    id: bigint;
    name: String;

    resourceLinks: String[];
    description: String;

    constructor(id: bigint, name: String, resourceLinks: String[], description: String) {
        this.id = id;
        this.name = name;
        this.resourceLinks = resourceLinks;
        this.description = description;
    }
}