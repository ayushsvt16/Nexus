package com.nexus.nexusproject.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nexus.nexusproject.Repository.ExamResourceRepository;
import com.nexus.nexusproject.model.ExamResource;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;

@Service
public class ResourceService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);
    
    private final ImageKit imageKit;
    private final ExamResourceRepository repository;

    public ResourceService(ImageKit imageKit, ExamResourceRepository repository) {
        this.imageKit = imageKit;
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

        logger.info("Starting file upload to ImageKit: {}", file.getOriginalFilename());
        
        try {
            // Validate file
            if (file.isEmpty()) {
                throw new IOException("File is empty");
            }

            // Create file upload request for ImageKit
            FileCreateRequest fileCreateRequest = new FileCreateRequest(
                    file.getBytes(), 
                    file.getOriginalFilename()
            );
            // Set folder for organization (similar to Cloudinary folders)
            fileCreateRequest.setFolder("exam-vault");
            // Set use unique filename to avoid conflicts
            fileCreateRequest.setUseUniqueFileName(true);
            // Set tags for better organization and searchability
            List<String> tags = new ArrayList<>();
            if (subjectCode != null && !subjectCode.trim().isEmpty()) {
                tags.add(subjectCode.trim().toLowerCase());
            }
            if (branch != null && !branch.trim().isEmpty()) {
                tags.add(branch.trim().toLowerCase());
            }
            if (type != null && !type.trim().isEmpty()) {
                tags.add(type.replace(" ", "_").toLowerCase());
            }
            tags.add("semester_" + (semester != null ? semester : 1));
            tags.add("year_" + (year != null ? year : 2023));
            if (!tags.isEmpty()) {
                fileCreateRequest.setTags(tags);
            }
            // Set response fields to get additional metadata
            List<String> responseFields = new ArrayList<>();
            responseFields.add("fileId");
            responseFields.add("url");
            responseFields.add("thumbnail");
            responseFields.add("size");
            responseFields.add("fileType");
            fileCreateRequest.setResponseFields(responseFields);
            logger.debug("Uploading file with tags: {}", tags);
            // Upload to ImageKit
            Result result = null;
            try {
                result = imageKit.upload(fileCreateRequest);
            } catch (io.imagekit.sdk.exceptions.InternalServerException |
                     io.imagekit.sdk.exceptions.BadRequestException |
                     io.imagekit.sdk.exceptions.UnknownException |
                     io.imagekit.sdk.exceptions.ForbiddenException |
                     io.imagekit.sdk.exceptions.TooManyRequestsException |
                     io.imagekit.sdk.exceptions.UnauthorizedException ex) {
                logger.error("ImageKit upload failed due to SDK exception", ex);
                throw new IOException("ImageKit SDK error: " + ex.getMessage(), ex);
            }
            if (result == null || result.getFileId() == null || result.getFileId().isEmpty()) {
                logger.error("ImageKit upload failed. No fileId returned.");
                throw new IOException("ImageKit upload failed. No fileId returned.");
            }
            logger.info("File uploaded successfully to ImageKit. File ID: {}", result.getFileId());
            String fileUrl = result.getUrl();

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
            
            // Store ImageKit file ID for future operations (if you added this field)
            // resource.setImageKitFileId(result.getFileId());
            
            // Store thumbnail URL if available (if you added this field)
            // if (result.getThumbnail() != null && !result.getThumbnail().isEmpty()) {
            //     resource.setThumbnailUrl(result.getThumbnail());
            // }

            ExamResource savedResource = repository.save(resource);
            logger.info("Resource metadata saved to database with ID: {}", savedResource.getId());
            
            return savedResource;
            
        } catch (IOException e) {
            logger.error("Failed to upload file to ImageKit", e);
            throw e;
        } catch (RuntimeException e) {
            logger.error("Unexpected runtime error during upload", e);
            throw new IOException("Unexpected runtime error during upload: " + e.getMessage(), e);
        }
    }

    public List<ExamResource> fetchResources(Integer semester, String branch, String type) {
        logger.debug("Fetching resources with semester: {}, branch: {}, type: {}", semester, branch, type);
        
        // Default values - same as before
        int sem = (semester != null) ? semester : 1;
        String br = (branch != null) ? branch : "CSE";
        String tp = (type != null) ? type : "Mid Sem";
        
        List<ExamResource> resources = repository.findBySemesterAndBranchAndType(sem, br, tp);
        logger.info("Found {} resources for semester: {}, branch: {}, type: {}", 
                    resources.size(), sem, br, tp);
        
        return resources;
    }
    
    // Additional utility methods you might want to add
    
    /**
     * Delete a resource from both ImageKit and database
     */
    public void deleteResource(Long resourceId) throws IOException {
        logger.info("Deleting resource with ID: {}", resourceId);
        
        ExamResource resource = repository.findById(resourceId)
                .orElseThrow(() -> new IOException("Resource not found with ID: " + resourceId));
        
        // If you stored ImageKit file ID, you can delete from ImageKit too
        // try {
        //     imageKit.deleteFile(resource.getImageKitFileId());
        //     logger.info("File deleted from ImageKit: {}", resource.getImageKitFileId());
        // } catch (Exception e) {
        //     logger.warn("Failed to delete file from ImageKit: {}", e.getMessage());
        // }
        
        repository.delete(resource);
        logger.info("Resource deleted from database: {}", resourceId);
    }
    
    /**
     * Get a signed URL for secure file access
     */
    public String getSignedUrl(String filePath, int expireSeconds) {
        try {
            Map<String, Object> options = new HashMap<>();
            options.put("path", filePath);
            options.put("signed", true);
            options.put("expireSeconds", expireSeconds);
            
            String signedUrl = imageKit.getUrl(options);
            logger.debug("Generated signed URL for path: {}", filePath);
            return signedUrl;
            
        } catch (Exception e) {
            logger.error("Failed to generate signed URL for path: {}", filePath, e);
            return null;
        }
    }
    
    /**
     * Get a transformed URL (e.g., for thumbnails)
     */
    public String getTransformedUrl(String filePath, int width, int height) {
        try {
            Map<String, Object> options = new HashMap<>();
            options.put("path", filePath);
            
            List<Map<String, String>> transformations = new ArrayList<>();
            Map<String, String> transform = new HashMap<>();
            transform.put("width", String.valueOf(width));
            transform.put("height", String.valueOf(height));
            transform.put("crop", "maintain_ratio");
            transformations.add(transform);
            
            options.put("transformation", transformations);
            
            String transformedUrl = imageKit.getUrl(options);
            logger.debug("Generated transformed URL for path: {}", filePath);
            return transformedUrl;
            
        } catch (Exception e) {
            logger.error("Failed to generate transformed URL for path: {}", filePath, e);
            return null;
        }
    }
}