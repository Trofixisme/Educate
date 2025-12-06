package com.group.InternMap.Contoller;

import com.group.InternMap.Model.Roadmap.Roadmap;
import com.group.InternMap.Services.RoadmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.group.InternMap.Repo.RepositoryAccessors.allRoadmaps;

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
        model.addAttribute("roadmap", new Roadmap());
        return "roadmap/form";
    }


    //Create new roadmap
    @PostMapping
    public String createRoadmap(@ModelAttribute Roadmap roadmap) {
        allRoadmaps.add(roadmap);
        return "redirect:/roadmaps/" + roadmap.getRoadmapID();
    }

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