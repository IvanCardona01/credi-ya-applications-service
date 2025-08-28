package co.com.applicationsservice.model.constants;

import java.math.BigDecimal;

public final class BusinessConstants {
    private BusinessConstants() {}

    public static final int MIN_LOAN_MONTHS = 1;
    public static final int MAX_LOAN_MONTHS = 84;
    public static final BigDecimal MIN_AMOUNT = new BigDecimal(1000000);
    public static final BigDecimal MAX_AMOUNT = new BigDecimal(500000000);

    public static final Long DEFAULT_STATUS_ID = 1L;
}
