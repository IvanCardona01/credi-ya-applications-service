package co.com.applicationsservice.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Loan status information")
public record LoanStatusResponseDTO(
        @Schema(description = "Status unique identifier", 
                example = "1")
        Long id,
        
        @Schema(description = "Status name", 
                example = "PENDING")
        String name,
        
        @Schema(description = "Status detailed description", 
                example = "Application is pending review")
        String description
) {
}
