package com.nexus.nexusproject.Controller.lambda;

import java.util.Base64;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.nexusproject.DTO.ApiGatewayResponse;
import com.nexus.nexusproject.DTO.UploadResourceRequest;
import com.nexus.nexusproject.DTO.UploadResourceResponse;
import com.nexus.nexusproject.Service.ResourceService;
import com.nexus.nexusproject.Service.S3service;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class UploadResourceHandler extends BaseLambdaHandler {

  @Autowired
  private ApiGatewayResponse apiGatewayResponse;

  @Autowired
  private ResourceService examResourceService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private Validator validator;
  
  @Override
  protected APIGatewayProxyResponseEvent processRequest(APIGatewayProxyRequestEvent input, Context context) throws Exception {
      log.info("UploadResourceHandler received request: {}", input.getBody());
      UploadResourceRequest uploadRequest = parseRequestBody(input, UploadResourceRequest.class);
      validateUploadRequest(uploadRequest);
      UploadResourceResponse response = examResourceService.uploadData(uploadRequest);  
      return apiGatewayResponse.createSuccessResponse(response);
  }

  /**
     * Parse request body as specific type
     */
    protected <T> T parseRequestBody(APIGatewayProxyRequestEvent input, Class<T> clazz) throws Exception {
        if (input.getBody() == null || input.getBody().trim().isEmpty()) {
            throw new ValidationException("Request body is required");
        }
        return objectMapper.readValue(input.getBody(), clazz);
    }

    /**
     * Validate upload request
     */
    private void validateUploadRequest(UploadResourceRequest request) throws ValidationException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        Set<ConstraintViolation<UploadResourceRequest>> violations = validator.validate(request);
        
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<UploadResourceRequest> violation : violations) {
                sb.append(violation.getMessage()).append("; ");
            }
            throw new ValidationException("Validation failed: " + sb.toString());
        }
        
        // Additional custom validations
        if (request.getFileData() == null || request.getFileData().trim().isEmpty()) {
            throw new ValidationException("File data is required");
        }
        
        try {
            Base64.getDecoder().decode(request.getFileData());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid base64 file data");
        }
    }
  
}
