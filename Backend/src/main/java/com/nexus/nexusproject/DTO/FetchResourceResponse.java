package com.nexus.nexusproject.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nexus.nexusproject.model.ExamResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FetchResourceResponse {

   @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("resources")
    private List<ExamResource> resources;
    
    @JsonProperty("totalCount")
    private Integer totalCount;
  
}
