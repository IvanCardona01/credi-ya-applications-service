package co.com.applicationsservice.api.dto.request;

import java.math.BigDecimal;

public record CreateApplicationDTO(
        String clientDocument,
        BigDecimal creditAmount,
        Integer months,
        Long typeId
) {
}
