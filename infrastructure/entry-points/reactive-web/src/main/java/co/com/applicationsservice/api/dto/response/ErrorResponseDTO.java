package co.com.applicationsservice.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Error response structure")
public record ErrorResponseDTO(
        @Schema(description = "Error code identifier", 
                example = "VALIDATION_ERROR")
        String code,
        
        @Schema(description = "Human readable error message", 
                example = "error message")
        String message,
        
        @Schema(description = "Timestamp when the error occurred", 
                example = "2024-01-15T10:30:00")
        LocalDateTime timestamp,
        
        @Schema(description = "API path where the error occurred", 
                example = "/api/v1/application")
        String path
) {
    public static ErrorResponseDTO of(String code, String message, String path) {
        return new ErrorResponseDTO(code, message, LocalDateTime.now(), path);
    }

    public static ErrorResponseDTO of(String code, String message) {
        return new ErrorResponseDTO(code, message, LocalDateTime.now(), null);
    }
}
