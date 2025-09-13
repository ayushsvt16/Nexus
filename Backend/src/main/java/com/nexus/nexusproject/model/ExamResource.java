package com.nexus.nexusproject.model;// path of the directory

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Data
@Getter
@Setter
@Builder
@Table(name = "exam_resources") // table name 
public class ExamResource {
       
        @Id
        @JsonProperty("id")
        private Long id;
        
        @JsonProperty("subjectCode")
        private String subjectCode;
        
        @JsonProperty("subjectName")
        private String subjectName;
        
        @JsonProperty("professorName")
        private String professorName;
        
        @JsonProperty("type")
        private String type;
        
        @JsonProperty("semester")
        private Integer semester;
        
        @JsonProperty("year")
        private Integer year;
        
        @JsonProperty("branch")
        private String branch;
        
        @JsonProperty("fileUrl")
        private String fileUrl;
        
        @JsonProperty("presignedUrl")
        private String presignedUrl;
        
        @JsonProperty("s3Key")
        private String s3Key;
        
        @JsonProperty("fileSize")
        private Long fileSize;
        
        @JsonProperty("uploadedAt")
        private LocalDateTime uploadedAt;
}