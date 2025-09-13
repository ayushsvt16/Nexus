package com.nexus.nexusproject.DTO;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadResourceResponse {
  
  @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("resourceId")
    private Long resourceId;
    
    @JsonProperty("s3Key")
    private String s3Key;
    
    @JsonProperty("fileUrl")
    private String fileUrl;
    
    @JsonProperty("fileSize")
    private Long fileSize;
    
    @JsonProperty("uploadedAt")
    private LocalDateTime uploadedAt;
    
    @JsonProperty("subjectCode")
    private String subjectCode;
    
    @JsonProperty("subjectName")
    private String subjectName;
    
    @JsonProperty("branch")
    private String branch;
    
    @JsonProperty("semester")
    private Integer semester;
    
    @JsonProperty("year")
    private Integer year;
    
    @JsonProperty("type")
    private String type;
    
}
