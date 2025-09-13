package com.nexus.nexusproject.Service;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nexus.nexusproject.DTO.StudyNoteResponse;
import com.nexus.nexusproject.Repository.StudyNotesRepository;

import io.imagekit.sdk.ImageKit;

@Service
public class StudyNoteService {

    private static final  Logger logger = LoggerFactory.getLogger(StudyNoteService.class);

    @Autowired
    private StudyNotesRepository studyNoteRepository;
    
    @Autowired
    private ImageKit imagekit;

    public StudyNoteResponse uploadNote(MultipartFile file, Integer semester, String subjectCode, String branch,
            Integer batchYear) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<StudyNoteResponse> getNotes(String subjectCode, Integer batchYear) {
        // TODO Auto-generated method stub
        return null;
    }

}
