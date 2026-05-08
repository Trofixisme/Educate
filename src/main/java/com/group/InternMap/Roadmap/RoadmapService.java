package com.group.InternMap.Roadmap;

import com.group.InternMap.DTO.RoadmapModuleSkill;
import com.group.InternMap.Skill.Skill;
import com.group.InternMap.Skill.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoadmapService {

    RoadmapRepo roadmapRepo;
    SkillRepo skillRepo;
    RoadmapModuleRepo roadmapModuleRepo;

    @Autowired
    public RoadmapService(RoadmapRepo roadmapRepo, SkillRepo skillRepo, RoadmapModuleRepo roadmapModuleRepo) {
        // without this, the controller will have empty data
        this.roadmapRepo = roadmapRepo;
        this.skillRepo = skillRepo;
        this.roadmapModuleRepo = roadmapModuleRepo;
    }

    public int countTotalModules(Roadmap roadmap) {
        return roadmap.getAllModules().stream()
                .mapToInt(module -> module.getAllSkills() != null ? module.getAllSkills().size() : 0)
                .sum();
    }

    public Roadmap findRoadmapById(Long roadmapId) throws IllegalArgumentException {
        Roadmap roadmap;
        if (roadmapId == null) {
            throw new IllegalArgumentException("Roadmap must be provided");
        } else{
            roadmap = roadmapRepo.findById(roadmapId).orElseThrow(() -> new RuntimeException("Roadmap not found"));
            //this line gets the roadmap if exists. If not, throws exception
        }
        return roadmap;
    }

    public void deleteById(Long roadmapId) {
        Roadmap roadmap = roadmapRepo.findById(roadmapId).orElseThrow(() -> new RuntimeException("Roadmap not found"));
        roadmapRepo.delete(roadmap);
    }

    //Search roadmap by name
    public Roadmap findByName(String roadmapName) {
        Roadmap roadmap;
        if (roadmapName == null) {
            throw new IllegalArgumentException("Roadmap name must be provided");
        } else{
            roadmap = roadmapRepo.findByName(roadmapName).orElseThrow(() -> new RuntimeException("Roadmap not found"));
        }
        return roadmap;
    }

    public List<Roadmap> findAll(){
        return roadmapRepo.findAll();
    }
    public Roadmap applyPatch(Roadmap existing, RoadmapModuleSkill dto) {
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getModules() != null) dto.getModules().forEach(modData -> applyModulePatch(existing, modData));
        return roadmapRepo.save(existing);
    }

    private void applyModulePatch(Roadmap roadmap, RoadmapModuleSkill.ModuleData modData) {
        if (modData.is_deleted() && modData.getId() != null) {
            roadmapModuleRepo.deleteById(modData.getId());
            return;
        }
        if (modData.getId() == null) {
            RoadmapModule newModule = new RoadmapModule(modData.getName(), modData.getDescription());
            modData.getSkills().stream()
                    .filter(s -> !s.is_deleted())
                    .forEach(s -> newModule.addSkills(new Skill(s.getName(), s.getDescription(), s.getLinks())));
            roadmap.addModules(roadmapModuleRepo.save(newModule));
            return;
        }
        roadmapModuleRepo.findById(modData.getId()).ifPresent(m -> applySkillPatches(m, modData));
    }

    private void applySkillPatches(RoadmapModule module, RoadmapModuleSkill.ModuleData modData) {
        if (modData.getName() != null) module.setName(modData.getName());
        if (modData.getDescription() != null) module.setDescription(modData.getDescription());
        modData.getSkills().forEach(skillData -> applySkillPatch(module, skillData));
        roadmapModuleRepo.save(module);
    }

    private void applySkillPatch(RoadmapModule module, RoadmapModuleSkill.SkillData skillData) {
        if (skillData.is_deleted() && skillData.getId() != null) {
            skillRepo.deleteById(skillData.getId());
            return;
        }
        if (skillData.getId() == null) {
            module.addSkills(skillRepo.save(new Skill(skillData.getName(), skillData.getDescription(), skillData.getLinks())));
            return;
        }
        skillRepo.findById(skillData.getId()).ifPresent(s -> {
            if (skillData.getName() != null) s.setName(skillData.getName());
            if (skillData.getDescription() != null) s.setDescription(skillData.getDescription());
            if (skillData.getLinks() != null) s.setLinks(skillData.getLinks());
            skillRepo.save(s);
        });
    }
}
