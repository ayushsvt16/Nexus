package com.nexus.nexusproject.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.nexusproject.Repository.StudyNotesRepository;

import io.imagekit.sdk.ImageKit;

@Service
public class StudyNoteService {

    private static final  Logger logger = LoggerFactory.getLogger(StudyNoteService.class);

    @Autowired
    private StudyNotesRepository studyNoteRepository;
    
    @Autowired
    private ImageKit imagekit;

    




}
