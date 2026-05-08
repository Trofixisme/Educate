package com.group.InternMap.Admin;

import com.group.InternMap.DTO.DashboardResponse;
import com.group.InternMap.DTO.RoadmapModuleSkill;
import com.group.InternMap.Job.JobPosting;
import com.group.InternMap.Job.JobPostingService;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapModuleRepo;
import com.group.InternMap.Roadmap.RoadmapRepo;
import com.group.InternMap.Roadmap.RoadmapService;
import com.group.InternMap.Skill.SkillRepo;
import com.group.InternMap.User.UserRole;
import com.group.InternMap.User.UserService;
import com.group.InternMap.User.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class RestAdminController {

    RoadmapRepo roadmapRepo;
    SkillRepo skillRepo;
    RoadmapModuleRepo roadmapModuleRepo;
    UserService userService;
    RoadmapService roadmapService;

    @Autowired
    public RestAdminController(RoadmapRepo roadmapRepo, SkillRepo skillRepo, RoadmapModuleRepo roadmapModuleRepo, UserService userService, RoadmapService roadmapService) {
        this.roadmapRepo = roadmapRepo;
        this.skillRepo = skillRepo;
        this.roadmapModuleRepo = roadmapModuleRepo;
        this.userService = userService;
        this.roadmapService = roadmapService;
    }

    @PostMapping("/")
    public void registerAdmin(HttpServletRequest request, @RequestBody Admin user) throws ServletException {
        user.setRole(UserRole.ADMIN);
        userService.register(user, request);
    }

    @GetMapping("/dashboard")
    public DashboardResponse getAdminDashboard(Authentication authentication ) throws AccessDeniedException {
         if (authentication == null || !authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]")) {
            throw new AccessDeniedException("Access denied");
        }

        List<Users> users = userService.findall();
        List<Roadmap> roadmaps = roadmapService.findAll();

        return new DashboardResponse(users, roadmaps);
    }

    @DeleteMapping("/dashboard/delete/{email}")
    public void deleteUser(@PathVariable String email, Authentication authentication) throws AccessDeniedException {
        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]")) {
            userService.deleteByEmail(email);
        } else {
            throw new AccessDeniedException("Access denied, you are not an admin");
        }
    }

    @DeleteMapping("/dashboard/delete/roadmap/{id}")
    public void deleteRoadmap(@PathVariable Long id, Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]")) {
            roadmapRepo.deleteById(id);
        } else {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Access denied, you are not an admin");
        }
    }

    @PostMapping("/roadmap/create/")
    public void createRoadmap(@RequestBody Roadmap roadmap, Authentication authentication) throws AccessDeniedException {
        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]")) {
            roadmapRepo.save(roadmap);
        } else {
            throw new AccessDeniedException("Access denied, you are not an admin");
        }
    }

    @PostMapping("/update")
    public void updateAdmin(@RequestBody Admin admin, Authentication authentication) throws AccessDeniedException {
        if (authentication != null && authentication.getAuthorities().toString().equals("[ROLE_" + UserRole.ADMIN + "]")) {
            assert authentication != null;
            Users adminToUpdate = userService.searchByEmail(authentication.getName())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            userService.updateUser(adminToUpdate, admin);
        } else {
            throw new AccessDeniedException("Access denied, you are not an admin");
        }
    }
}