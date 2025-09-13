package com.nexus.nexusproject.DTO;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class S3FileMetaData {

        private String s3Key;
        private Long contentLength;
        private String contentType;
        private java.time.Instant lastModified;
        private String eTag;
        private Map<String, String> metadata;
  
}
