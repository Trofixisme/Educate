package com.group.InternMap.Roadmap;

import com.group.InternMap.DTO.RoadmapModuleSkill;
import com.group.InternMap.User.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/roadmap")
public class RoadmapController {

    private final RoadmapService roadmapService;
    RoadmapRepo roadmapRepo;

    @Autowired
    RoadmapController(RoadmapRepo roadmapRepo, RoadmapService roadmapService) {
        this.roadmapRepo = roadmapRepo;
        this.roadmapService = roadmapService;
    }
    //updating part for roadmap
    //getting 1 roadmap by id so i can edit it
    @GetMapping("/{id}")
    public Roadmap getRoadmap(@PathVariable long id) {
        return roadmapRepo.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Roadmap not found"));
    }
    @PatchMapping("/{id}")
    public Roadmap patchRoadmap(@PathVariable long id, @RequestBody RoadmapModuleSkill dto, Authentication authentication) {
        if (authentication == null || !authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]")) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of " + UserRole.ADMIN + " to proceed");
        }
        Roadmap existing = roadmapRepo.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Roadmap not found"));
        return roadmapService.applyPatch(existing, dto);
    }

    @GetMapping("/roadmaps")
    public List<Roadmap> getRoadmaps() {
        return roadmapService.findAll();
    }

    @PostMapping("/roadmaps/{id}/delete")
    public void deleteRoadmap(@PathVariable Long id, Authentication authentication) {

        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]")) {
            roadmapRepo.deleteById(id);

        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "User must be of "+ UserRole.ADMIN +" to proceed");
        }
    }
}
