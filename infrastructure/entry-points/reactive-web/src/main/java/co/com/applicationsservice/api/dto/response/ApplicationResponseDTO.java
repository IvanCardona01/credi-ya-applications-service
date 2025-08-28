package co.com.applicationsservice.api.dto.response;

import java.math.BigDecimal;

public record ApplicationResponseDTO(
        Long id,
        String clientDocument,
        BigDecimal creditAmount,
        Integer months,

        LoanStatusResponseDTO loanStatus,
        LoanTypeResponseDTO loanType
) {
}
