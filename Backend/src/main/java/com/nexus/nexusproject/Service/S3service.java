package com.nexus.nexusproject.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nexus.nexusproject.DTO.S3UploadResult;
import com.nexus.nexusproject.constants.S3Constants;
import software.amazon.awssdk.core.sync.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

@RequiredArgsConstructor
@Log4j2
@Service
public class S3service {

  @Autowired
  private S3Presigner s3Presigner;

  @Autowired
  private S3Client s3Client;

  /**
     * Generate presigned URL for secure file access
     */
    public String generatePresignedUrl(String s3Key, Duration expiration, String bucketName) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();
            
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(expiration)
                    .getObjectRequest(getObjectRequest)
                    .build();
            
            PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
            
            log.debug("Generated presigned URL for key: {}", s3Key);
            return presignedRequest.url().toString();
            
        } catch (Exception e) {
            log.error("Failed to generate presigned URL for key: {}", s3Key, e);
            return null;
        }
    }

     /**
     * Upload file to S3 bucket
     */
    public S3UploadResult uploadFile(byte[] fileData, String originalFilename, String contentType, 
                                   String branch, Integer semester, String type, String bucketName) throws IOException {
        
        validateFile(fileData, contentType);
        
        // Generate unique filename to avoid conflicts
        String fileExtension = getFileExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
        
        // Create S3 key with organized folder structure
        String s3Key = String.format(S3Constants.S3_KEY_PATTERN, 
                S3Constants.BUCKET_FOLDER_PREFIX, 
                branch, 
                semester, 
                type.replace(" ", "_").toLowerCase(), 
                uniqueFilename);
        
        try {
            // Create metadata for the object
            Map<String, String> metadata = new HashMap<>();
            metadata.put("original-filename", originalFilename);
            metadata.put("branch", branch);
            metadata.put("semester", semester.toString());
            metadata.put("type", type);
            metadata.put("upload-timestamp", String.valueOf(System.currentTimeMillis()));
            
            // Create put object request
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType(contentType)
                    .contentLength((long) fileData.length)
                    .metadata(metadata)
                    .serverSideEncryption(ServerSideEncryption.AES256)
                    .build();
            
            // Upload the file
            PutObjectResponse response = s3Client.putObject(putRequest, RequestBody.fromBytes(fileData));
            
            log.info("File uploaded successfully to S3. Key: {}, ETag: {}", s3Key, response.eTag());
            
            // Generate public URL
            String fileUrl = generatePublicUrl(s3Key, bucketName);
            
            return new S3UploadResult(s3Key, fileUrl, fileData.length, response.eTag());
            
        } catch (Exception e) {
            log.error("Failed to upload file to S3. Key: {}", s3Key, e);
            throw new IOException("Failed to upload file to S3: " + e.getMessage(), e);
        }
    }


    /**
     * Validate file before upload
     */
    private void validateFile(byte[] fileData, String contentType) throws IOException {
        if (fileData == null || fileData.length == 0) {
            throw new IOException("File data is empty");
        }
        
        if (fileData.length > S3Constants.MAX_FILE_SIZE) {
            throw new IOException(S3Constants.FILE_SIZE_EXCEEDED_MESSAGE + 
                    ". Maximum allowed: " + (S3Constants.MAX_FILE_SIZE / 1024 / 1024) + "MB");
        }
        
        if (contentType == null || contentType.trim().isEmpty()) {
            throw new IOException("Content type is required");
        }
        
        boolean isValidType = Arrays.stream(S3Constants.ALLOWED_FILE_TYPES)
                .anyMatch(allowedType -> allowedType.equalsIgnoreCase(contentType));
        
        if (!isValidType) {
            throw new IOException(S3Constants.INVALID_FILE_TYPE_MESSAGE + ": " + contentType);
        }
    }

     /**
     * Extract file extension from filename
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return "";
        }
        
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
            return "";
        }
        
        return filename.substring(lastDotIndex);
    }

     /**
     * Generate public URL for S3 object
     */
    private String generatePublicUrl(String s3Key, String bucketName) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, s3Key);
    }
}
