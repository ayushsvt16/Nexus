package com.nexus.nexusproject.Controller.lambda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.nexus.nexusproject.DTO.ApiGatewayResponse;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Base class for all Lambda handlers providing common functionality
 */
@RequiredArgsConstructor
@Log4j2
@RestController
public abstract class BaseLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  @Autowired
  private ApiGatewayResponse apiGatewayResponse;

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
      try {
            log.info("Processing request: {}", input.getPath());
            return processRequest(input, context);
        } catch (ValidationException e) {
            log.error("Validation error: {}", e.getMessage());
            return apiGatewayResponse.createErrorResponse(400, "Validation Error", e.getMessage());
        } catch (BusinessException e) {
            log.error("Business logic error: {}", e.getMessage());
            return apiGatewayResponse.createErrorResponse(422, "Business Error", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error processing request", e);
            return apiGatewayResponse.createErrorResponse(500, "Internal Server Error", "An unexpected error occurred");
        }
  }

  /**
     * Abstract method to be implemented by concrete handlers
     */
    protected abstract APIGatewayProxyResponseEvent processRequest(APIGatewayProxyRequestEvent input, Context context) throws Exception;


     /**
     * Custom business logic exception
     */
    public static class BusinessException extends Exception {
        public BusinessException(String message) {
            super(message);
        }
        
        public BusinessException(String message, Throwable cause) {
            super(message, cause);
      
        }
      }

     /**
     * Extract query parameter with default value
     */
    protected String getQueryParameter(APIGatewayProxyRequestEvent input, String paramName, String defaultValue) {
        if (input.getQueryStringParameters() == null) {
            return defaultValue;
        }
        return input.getQueryStringParameters().getOrDefault(paramName, defaultValue);
    }
    
    /**
     * Extract query parameter as Integer
     */
    protected Integer getQueryParameterAsInteger(APIGatewayProxyRequestEvent input, String paramName, Integer defaultValue) {
        String value = getQueryParameter(input, paramName, null);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.warn("Invalid integer parameter {}: {}", paramName, value);
            return defaultValue;
        }
    }
}
