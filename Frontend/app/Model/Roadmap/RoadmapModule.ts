import type {Skill} from "~/Model/Roadmap/Skill";

export class RoadmapModule {

    id: bigint;
    name: String;
    description: String;

    allSkills: Skill[];

    constructor(id: bigint, name: String, description: String, skills: Skill[]) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allSkills = skills;
    }
}