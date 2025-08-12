package com.nexus.nexusproject.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nexus.nexusproject.Service.ResourceService;
import com.nexus.nexusproject.model.ExamResource;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "*")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                    @RequestParam String subjectCode,
                                    @RequestParam String subjectName,
                                    @RequestParam String professorName,
                                    @RequestParam String type,      // Mid Sem / End Sem
                                    @RequestParam Integer semester, // 1-8
                                    @RequestParam Integer year,
                                    @RequestParam String branch) {
        try {
            ExamResource saved = resourceService.uploadResource(file, subjectCode, subjectName, professorName, type, semester, year, branch);
            return ResponseEntity.ok(saved);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ExamResource>> fetch(@RequestParam(required = false) Integer semester,
                                                    @RequestParam(required = false) String branch,
                                                    @RequestParam(required = false) String type,
                                                    @RequestParam(required = false) Integer year) {
        return ResponseEntity.ok(resourceService.fetchResources(semester, branch, type, year));
    }
}
