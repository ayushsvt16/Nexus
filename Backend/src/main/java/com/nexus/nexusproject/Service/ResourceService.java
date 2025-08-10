package com.nexus.nexusproject.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nexus.nexusproject.Repository.ExamResourceRepository;
import com.nexus.nexusproject.model.ExamResource;

@Service
public class ResourceService {

    private final Cloudinary cloudinary;
    private final ExamResourceRepository repository;

    @Autowired
    public ResourceService(Cloudinary cloudinary, ExamResourceRepository repository) {
        this.cloudinary = cloudinary;
        this.repository = repository;
    }

    public ExamResource uploadResource(MultipartFile file,
                                       String subjectCode,
                                       String subjectName,
                                       String professorName,
                                       String type,
                                       Integer semester,
                                       Integer year,
                                       String branch) throws IOException {

        // Upload the file to Cloudinary (as "raw" for PDFs)
        @SuppressWarnings("unchecked")
        Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "raw",
                        "folder", "exam-vault"
                )
        );

        String fileUrl = String.valueOf(uploadResult.get("secure_url"));

        // Save metadata to Postgres
        ExamResource resource = new ExamResource();
        resource.setSubjectCode(subjectCode != null ? subjectCode.trim() : null);
        resource.setSubjectName(subjectName != null ? subjectName.trim() : null);
        resource.setProfessorName(professorName != null ? professorName.trim() : null);
        resource.setType(type);
        resource.setSemester(semester);
        resource.setYear(year);
        resource.setBranch(branch);
        resource.setFileUrl(fileUrl);

        return repository.save(resource);
    }

    public List<ExamResource> fetchResources(Integer semester, String branch, String type) {
        // Default: Sem 1, CSE, Mid Sem
        int sem = (semester != null) ? semester : 1;
        String br = (branch != null) ? branch : "CSE";
        String tp = (type != null) ? type : "Mid Sem";
        return repository.findBySemesterAndBranchAndType(sem, br, tp);
    }
}
