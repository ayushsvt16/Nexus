package com.nexus.nexusproject.Controller;// path of the directory

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

@RestController // contains controller + response body
@RequestMapping("/api/resources") // base path for all methods down
@CrossOrigin(origins = "*") // cors enable to allow all origins mtb frontend se request aa sakti hai
public class ResourceController {

    @Autowired
    private ResourceService resourceService; // inject resourceService dependency bean mtb object bana diya bean ke liye

    @PostMapping("/upload") // post mapping for file upload

    //  <?> iska mtb reponse body bhi return ho sakti string bhi integer bhi kuch bhii
    // @requestparam query se info nikalkr argument me save krta hai jaise subjectcode ko input request se liya and as a string store kr liya

    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, // file upload ke liye MultipartFile use hota hai
                                    @RequestParam String subjectCode,
                                    @RequestParam String subjectName,
                                    @RequestParam String professorName,
                                    @RequestParam String type,      // Mid Sem / End Sem
                                    @RequestParam Integer semester, // 1-8
                                    @RequestParam Integer year,
                                    @RequestParam String branch) {
      //  try {
            // resource service ye sab data ko store krta hai cloud service or somewhere
            //exam resource ek entity hai jo ek exam paper store hogya system me 
            // ExamResource saved = resourceService.uploadResource(file, subjectCode, subjectName, professorName, type, semester, year, branch);
            // return ResponseEntity.ok(saved);
            return ResponseEntity.ok(ExamResource.builder().subjectCode(subjectCode).subjectName(subjectName).professorName(professorName).type(type).semester(semester).year(year).branch(branch).build());
      //  } catch (IOException e) {
          //  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
      //  }
    }

    @GetMapping // fetch resources based on query parameters
    public ResponseEntity<List<ExamResource>> fetch(@RequestParam(required = false) Integer semester,
                                                    @RequestParam(required = false) String branch,
                                                    @RequestParam(required = false) String type,
                                                    @RequestParam(required = false) Integer year) {
                                                        // 4 hi parameters se fetch hoga
        return ResponseEntity.ok(resourceService.fetchResources(semester, branch, type, year));
        // resource service se data fetch hokr ayega and return hoga
    }
}
