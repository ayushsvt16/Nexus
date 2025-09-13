package com.nexus.nexusproject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadResourceRequest {

  @NotBlank(message = "Subject code is required")
    @JsonProperty("subjectCode")
    private String subjectCode;
    
    @NotBlank(message = "Subject name is required")
    @JsonProperty("subjectName")
    private String subjectName;
    
    @NotBlank(message = "Professor name is required")
    @JsonProperty("professorName")
    private String professorName;
    
    @NotBlank(message = "Type is required")
    @JsonProperty("type")
    private String type; // Mid Sem / End Sem
    
    @NotNull(message = "Semester is required")
    @Min(value = 1, message = "Semester must be between 1 and 8")
    @Max(value = 8, message = "Semester must be between 1 and 8")
    @JsonProperty("semester")
    private Integer semester;
    
    @NotNull(message = "Year is required")
    @Min(value = 2020, message = "Year must be valid")
    @Max(value = 2030, message = "Year must be valid")
    @JsonProperty("year")
    private Integer year;
    
    @NotBlank(message = "Branch is required")
    @JsonProperty("branch")
    private String branch;
    
    @NotBlank(message = "File name is required")
    @JsonProperty("fileName")
    private String fileName;
    
    @NotBlank(message = "Content type is required")
    @JsonProperty("contentType")
    private String contentType;
    
    @NotBlank(message = "File data is required")
    @JsonProperty("fileData")
    private String fileData; // Base64 encoded file data
  
}
