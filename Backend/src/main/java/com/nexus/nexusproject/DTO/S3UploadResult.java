package com.nexus.nexusproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class S3UploadResult {

  private  String s3Key;
  private  String fileUrl;
  private  long fileSize;
  private  String eTag;
  
}
