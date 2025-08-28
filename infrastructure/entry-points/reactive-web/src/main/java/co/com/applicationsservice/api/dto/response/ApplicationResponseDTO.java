package co.com.applicationsservice.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Loan application response with complete information")
public record ApplicationResponseDTO(
        @Schema(description = "Application unique identifier", 
                example = "1")
        Long id,
        
        @Schema(description = "Client's identification document number", 
                example = "12345678")
        String clientDocument,
        
        @Schema(description = "Requested credit amount", 
                example = "50000.00")
        BigDecimal creditAmount,
        
        @Schema(description = "Loan term in months", 
                example = "24")
        Integer months,

        @Schema(description = "Current status of the loan application")
        LoanStatusResponseDTO loanStatus,
        
        @Schema(description = "Type of loan requested")
        LoanTypeResponseDTO loanType
) {
}
