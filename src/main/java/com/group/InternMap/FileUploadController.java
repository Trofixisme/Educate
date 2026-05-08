package com.group.InternMap;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final String uploadDir = "uploads/logos/";
    @PostMapping("/upload")
    public String upload(@RequestParam("logo") MultipartFile file) throws IOException {

        Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads", "logos");
        Files.createDirectories(uploadDir); // ensure folder exists

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path targetPath = uploadDir.resolve(filename);

        file.transferTo(targetPath.toAbsolutePath()); // ← key fix: absolute path

        System.out.println("UPLOAD PATH: " + targetPath.toAbsolutePath());
        return "logos/" + filename;
    }

    @PostMapping("/upload/profile-picture")
    public String uploadProfilePicture(@RequestParam("profilePicture") MultipartFile file) throws IOException {
        Path uploadDir = Paths.get(System.getProperty("user.dir"), "uploads", "profile-pictures");
        Files.createDirectories(uploadDir);

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path targetPath = uploadDir.resolve(filename);
        file.transferTo(targetPath.toAbsolutePath());

        return "profile-pictures/" + filename;
    }
}