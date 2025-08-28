package co.com.applicationsservice.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Request data to create a new loan application")
public record CreateApplicationDTO(
        @Schema(description = "Client's identification document number", 
                example = "12345678", 
                required = true)
        String clientDocument,
        
        @Schema(description = "Requested credit amount", 
                example = "50000.00", 
                required = true)
        BigDecimal creditAmount,
        
        @Schema(description = "Loan term in months", 
                example = "24", 
                required = true)
        Integer months,
        
        @Schema(description = "ID of the loan type", 
                example = "1", 
                required = true)
        Long typeId
) {
}
