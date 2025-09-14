package com.nexus.nexusproject.constants;

public class S3Constants {

  private S3Constants() {
        // Utility class - prevent instantiation
    }
    
    // S3 Bucket Configuration
    public static final String DEFAULT_BUCKET_NAME = "nexus-exam-vault";
    public static final String BUCKET_FOLDER_PREFIX = "exam-resources";
    
    // File Upload Configuration
    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final String[] ALLOWED_FILE_TYPES = {
        "application/pdf",
        "image/jpeg",
        "image/png",
        "image/jpg",
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    };
    
    // S3 Object Key Patterns
    public static final String S3_KEY_PATTERN = "%s/%s/%d/%s/%s"; // folder/branch/semester/type/filename
    public static final String S3_PRESIGNED_URL_EXPIRATION_HOURS = "24";
    
    // Response Messages
    public static final String UPLOAD_SUCCESS_MESSAGE = "File uploaded successfully";
    public static final String UPLOAD_FAILED_MESSAGE = "File upload failed";
    public static final String FILE_NOT_FOUND_MESSAGE = "File not found";
    public static final String INVALID_FILE_TYPE_MESSAGE = "Invalid file type";
    public static final String FILE_SIZE_EXCEEDED_MESSAGE = "File size exceeds maximum limit";
    
    // Environment Variables
    public static final String ENV_S3_BUCKET_NAME = "S3_BUCKET_NAME";
    public static final String ENV_AWS_REGION = "AWS_REGION";
    public static final String ENV_RDS_ENDPOINT = "RDS_ENDPOINT";
    public static final String ENV_RDS_USERNAME = "RDS_USERNAME";
    public static final String ENV_RDS_PASSWORD = "RDS_PASSWORD";
    public static final String ENV_RDS_DATABASE = "RDS_DATABASE";
    
    // Default Values
    public static final String DEFAULT_AWS_REGION = "ap-south-1";
    public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    
    // Lambda Function Names
    public static final String UPLOAD_LAMBDA_FUNCTION = "nexus-upload-resource";
    public static final String FETCH_LAMBDA_FUNCTION = "nexus-fetch-resource";

    //BUCKET NAME
    public static final String S3_BUCKET_NAME = "nexus-bucket";

  
}
