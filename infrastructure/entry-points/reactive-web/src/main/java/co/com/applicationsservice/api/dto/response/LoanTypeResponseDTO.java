package co.com.applicationsservice.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Loan type information")
public record LoanTypeResponseDTO(
        @Schema(description = "Type unique identifier", 
                example = "1")
        Long id,
        
        @Schema(description = "Type name", 
                example = "PERSONAL")
        String name,
        
        @Schema(description = "Type detailed description", 
                example = "Personal loan for general purposes")
        String description
) {
}
