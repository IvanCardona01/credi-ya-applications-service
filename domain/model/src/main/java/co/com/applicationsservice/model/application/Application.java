package co.com.applicationsservice.model.application;
import co.com.applicationsservice.model.loanstatus.LoanStatus;
import co.com.applicationsservice.model.loantype.LoanType;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Application {
    private Long id;
    private String clientDocument;
    private BigDecimal creditAmount;
    private Integer months;

    private Long statusId;
    private Long typeId;

    private LoanStatus loanStatus;
    private LoanType loanType;
}
