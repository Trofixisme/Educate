package com.group.InternMap.Contoller;

import com.group.InternMap.Dto.RoadmapModuleSkill;
import com.group.InternMap.Model.Roadmap.Roadmap;
import com.group.InternMap.Model.Roadmap.RoadmapModule;
import com.group.InternMap.Services.RoadmapService;
import com.group.InternMap.Model.Roadmap.Skill.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


import static com.group.InternMap.Repo.RepositoryAccessors.*;

@Controller
@RequestMapping("/roadmaps")
public class RoadmapController {

    private RoadmapService roadmapService = new RoadmapService();

//    //Display all roadmaps
//    @GetMapping
//    public String listRoadmaps(Model model) {
//        model.addAttribute("roadmaps", allRoadmaps);
//        return "roadmap/list";
//    }

    //Display a specific roadmap with modules and skills
    @GetMapping("/{id}")
    public String viewRoadmap(@PathVariable UUID id, Model model) {
        try {
            Roadmap roadmap = roadmapService.findRoadmapbyId(id);
            int totalSkills = roadmap.getAllModules().stream()
                    .mapToInt(module -> module.getAllSkills() != null ? module.getAllSkills().size() : 0)
                    .sum();
            model.addAttribute("roadmap", roadmap);
            model.addAttribute("totalSkills", totalSkills);
            return "roadmap/view";
        }
        catch(Exception e){
            model.addAttribute(e);
            return "redirect:/roadmaps";
        }



        // Calculate statistics
//        int totalSkills = roadmap.getAllModules().stream()
//                .mapToInt(module -> module.getAllSkills() != null ? module.getAllSkills().size() : 0)
//                .sum();

//            int completedSkills = roadmap.getAllModules().stream()
//                    .flatMap(module -> module.getAllSkills() != null ? module.getAllSkills().stream() : null)
//                    .filter(::isCompleted)
//                    .mapToInt(skill -> 1)
//                    .sum();
//
//            double progress = totalSkills > 0 ? (completedSkills * 100.0 / totalSkills) : 0;

    }

    /**
     * Toggle skill completion status (form submission)
     */
//        @PostMapping("/skills/{skillId}/toggle")
//        public String toggleSkillCompletion(@PathVariable Long skillId,
//                                            @RequestParam Long roadmapId) {
//            skillService.toggleCompletion(skillId);
//            return "redirect:/roadmaps/" + roadmapId;
//        }

    //Display form to create new roadmap
    @GetMapping("/new")
    public String newRoadmap(Model model) {
        RoadmapModuleSkill dto = new RoadmapModuleSkill();
        // Initialize with one empty module and skill
        RoadmapModuleSkill.ModuleData module = new RoadmapModuleSkill.ModuleData();
        RoadmapModuleSkill.SkillData skill = new RoadmapModuleSkill.SkillData();
        module.getSkills().add(skill);
        dto.getModules().add(module);
        model.addAttribute("roadmaps", dto);
        return "roadmap/form";
    }
    @PostMapping("/new")
    public String createRoadmap(@ModelAttribute("roadmap") RoadmapModuleSkill dto) {
        Roadmap roadmap = dto.toRoadmap();

        allRoadmaps.add(roadmap);

        for (RoadmapModule module : roadmap.getAllModules()) {
            allmodules.add(module);
            for (Skill skill : module.getAllSkills()) {
                allskills.add(skill);
            }
        }

        return "redirect:/roadmaps/" + roadmap.getRoadmapID();
    }

//    @PostMapping
//    public String createRoadmap(@ModelAttribute("roadmap") RoadmapModuleSkill dto) {
//        Roadmap roadmap = dto.toRoadmap();
//        // Save
//        allRoadmaps.add(roadmap);
//        // Save modules and skills if needed
//        for (RoadmapModule module : roadmap.getAllModules()) {
//            allmodules.add(module);
//            for (Skill skill : module.getAllSkills()) {
//                allskills.add(skill);
//            }
//        }
//        return "redirect:/roadmaps/" + roadmap.getRoadmapID();
//    }
// PUT THIS AFTER /new


    //Display form to edit roadmap
    @GetMapping("/{id}/edit")
    public String editRoadmap(@PathVariable UUID id, Model model) {
        try {
            Roadmap roadmap = roadmapService.findRoadmapbyId(id);
            model.addAttribute("roadmap", roadmap);
            return "roadmap/form";
        } catch (Exception e) {
            model.addAttribute(e);
            return "redirect:/roadmaps";
        }
    }

     //Update roadmap
    @PostMapping("/{id}")
    public String updateRoadmap(@PathVariable UUID id, @ModelAttribute Roadmap roadmap) {
        allRoadmaps.add(roadmap);
        return "redirect:/roadmaps/" + id;
    }

    //Delete roadmap
    @PostMapping("/{id}/delete")
    public String deleteRoadmap(@PathVariable UUID id) {
        roadmapService.deleteById(id);
        return "redirect:/roadmaps";
    }
}