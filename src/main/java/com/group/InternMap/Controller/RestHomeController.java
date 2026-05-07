package com.group.InternMap.Controller;

import com.group.InternMap.Recruiter.RecruiterService;
import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.Roadmap.RoadmapService;
import com.group.InternMap.Student.Student;
import com.group.InternMap.User.JwtTokenProvider;
import com.group.InternMap.User.UserService;
import com.group.InternMap.User.Users;
import jakarta.servlet.http.HttpSession;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/REST")
public class RestHomeController {

    private final UserService userService;
    RoadmapService roadmapService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    RecruiterService recruiterService;

    public RestHomeController(RoadmapService roadmapService, RecruiterService recruiterService, UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.roadmapService = roadmapService;
        this.recruiterService = recruiterService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping
    public List<Roadmap> home() {

        return roadmapService.findAll();
    }

    @GetMapping("/profile")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Users showProfile(Authentication authentication) {
        Users user;

        if (authentication != null) {
            user = userService.searchByEmail(authentication.getName()).get();
            user.setPassword(null);
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Must be signed into an account");
        }

        return user;
    }

    //For some reason this just doesn't work
    @PostMapping("/logout")
    public void logout(HttpSession session) {

        session.invalidate();
    }
}
