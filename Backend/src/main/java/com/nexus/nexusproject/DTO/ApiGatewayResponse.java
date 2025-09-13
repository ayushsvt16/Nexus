package com.nexus.nexusproject.DTO;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@RequiredArgsConstructor
@Log4j2
@Component
public class ApiGatewayResponse {

    @Autowired
    private ObjectMapper objectMapper;
    
    /* 
     * Create a successful response
    */
    public APIGatewayProxyResponseEvent createSuccessResponse(Object data) {
        return createSuccessResponse(200, data);
    }
    
    /**
     * Create a successful response with custom status code
     */
    public APIGatewayProxyResponseEvent createSuccessResponse(int statusCode, Object data) {
        try {
            APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
            response.setStatusCode(statusCode);
            response.setHeaders(getCorsHeaders());
            response.setBody(objectMapper.writeValueAsString(data));
            return response;
        } catch (Exception e) {
            log.error("Error serializing response data", e);
            return createErrorResponse(500, "Serialization Error", "Failed to serialize response");
        }
    }
    
    /**
     * Create an error response
     */
  public APIGatewayProxyResponseEvent createErrorResponse(int statusCode, String error, String message) {
        try {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("error", error);
            errorBody.put("message", message);
            errorBody.put("timestamp", System.currentTimeMillis());
            
            APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
            response.setStatusCode(statusCode);
            response.setHeaders(getCorsHeaders());
            response.setBody(objectMapper.writeValueAsString(errorBody));
            return response;
        } catch (Exception e) {
            log.error("Error creating error response", e);
            APIGatewayProxyResponseEvent fallbackResponse = new APIGatewayProxyResponseEvent();
            fallbackResponse.setStatusCode(500);
            fallbackResponse.setHeaders(getCorsHeaders());
            fallbackResponse.setBody("{\"error\":\"Internal Server Error\",\"message\":\"Failed to process request\"}");
            return fallbackResponse;
        }
    }


     /**
     * Get CORS headers for responses
     */
    protected Map<String, String> getCorsHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.put("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
        return headers;
    }
    
  
}
