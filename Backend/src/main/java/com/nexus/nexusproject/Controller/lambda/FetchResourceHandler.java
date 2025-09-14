package com.nexus.nexusproject.Controller.lambda;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.nexus.nexusproject.DTO.ApiGatewayResponse;
import com.nexus.nexusproject.DTO.FetchResourceResponse;
import com.nexus.nexusproject.Service.ResourceService;
import com.nexus.nexusproject.Service.S3service;
import com.nexus.nexusproject.constants.S3Constants;
import com.nexus.nexusproject.model.ExamResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@RestController
public class FetchResourceHandler extends BaseLambdaHandler {

  @Autowired
  private ResourceService examResourceService;

  @Autowired
  private S3service s3service;

  @Autowired
  private ApiGatewayResponse apiGatewayResponse;
  
  @Override
  protected APIGatewayProxyResponseEvent processRequest(APIGatewayProxyRequestEvent input, Context context) throws Exception {
      log.info("FetchResourceHandler processing request: {}", input.getBody());
      Integer semester = getQueryParameterAsInteger(input, "semester", null);
      String branch = getQueryParameter(input, "branch", null);
      String type = getQueryParameter(input, "type", null);
      Integer year = getQueryParameterAsInteger(input, "year", null); 

      log.info("Fetch parameters - semester: {}, branch: {}, type: {}, year: {}", semester, branch, type, year);
      
      List<ExamResource> examResources = examResourceService.fetchExamResources(semester, branch, type, year);

      for (ExamResource resource : examResources) {
            if (resource.getS3Key() != null && !resource.getS3Key().trim().isEmpty()) {
                String presignedUrl = s3service.generatePresignedUrl(
                    resource.getS3Key(), 
                    Duration.ofHours(Long.parseLong(S3Constants.S3_PRESIGNED_URL_EXPIRATION_HOURS)),
                    S3Constants.S3_BUCKET_NAME
                );
                resource.setPresignedUrl(presignedUrl);
            }
      }
      
      return apiGatewayResponse.createSuccessResponse(FetchResourceResponse
      .builder().message("Resources fetched successfully").resources(examResources).success(false).totalCount(examResources.size()).build());
  }
}
