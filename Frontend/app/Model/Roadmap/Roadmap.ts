import type {RoadmapModule} from "~/Model/Roadmap/RoadmapModule";

export class Roadmap {

    id: bigint;
    name: String;

    allModules: RoadmapModule[];

    constructor(id: bigint, name: String, roadmapModules: RoadmapModule[]) {
        this.id = id;
        this.name = name;
        this.allModules = roadmapModules;
    }

}