package com.group.InternMap;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final String uploadDir = "uploads/logos/";

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path path = Paths.get(uploadDir + filename);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        return "logos/" + filename;
    }
}