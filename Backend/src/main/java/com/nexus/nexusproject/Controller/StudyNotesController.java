package com.nexus.nexusproject.Controller;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.nexus.nexusproject.DTO.StudyNoteResponse;
import com.nexus.nexusproject.Service.StudyNoteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
@RestController // controller + response body
@RequestMapping("/api/auth/notes")
@CrossOrigin(origins = "*")
public class StudyNotesController {

    @Autowired
    private StudyNoteService studyNoteService;

   @PostMapping("/upload")
public ResponseEntity<?> uploadNote(
        @RequestParam("file") MultipartFile file,
        @RequestParam Integer semester,
        @RequestParam String subjectCode,
        @RequestParam String branch,
        @RequestParam Integer batchYear) {

    try {
        if (semester == null || semester < 1 || semester > 8) {
            return ResponseEntity.badRequest().body("Semester must be between 1 and 8");
        }
        if (subjectCode == null || subjectCode.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Subject Code is required");
        }
        if (branch == null || branch.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Branch is required");
        }
        if (batchYear == null || batchYear < 2025 || batchYear > 2028) {
            return ResponseEntity.badRequest().body("Batch year must be between 2025 and 2028");
        }
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("PDF file is required");
        }

        StudyNoteResponse uploadedNote =
                studyNoteService.uploadNote(file, semester, subjectCode, branch, batchYear);

        return ResponseEntity.ok(uploadedNote);

    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Upload failed: " + e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error: " + e.getMessage());
    }
}

@GetMapping
public ResponseEntity<?> getNotes(
        @RequestParam(required = false) String subjectCode,
        @RequestParam(required = false) Integer batchYear) {
    try {
        List<StudyNoteResponse> notes = studyNoteService.getNotes(subjectCode, batchYear);
        return ResponseEntity.ok(notes);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to fetch notes: " + e.getMessage());
    }
}

}
