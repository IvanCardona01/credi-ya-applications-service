package co.com.applicationsservice.usecase.constants;

import co.com.applicationsservice.model.constants.BusinessConstants;

public final class UseCaseConstants {
    private UseCaseConstants() {}

    public static final String DOCUMENT_REQUIRED = "clientDocument is required";
    public static final String AMOUNT_REQUIRED = "creditAmount is required";
    public static final String MONTH_REQUIRED = "months is required";
    public static final String LOAN_TYPE_REQUIRED = "typeId is required";
    public static final String LOAN_TYPE_UNKNOW = "The typeId is invalid";
    public static final String DUPLICATE_APPLICATION = "An application already exists for this client document";

    public static final String USER_NOT_REGISTERED = "User is not registered for this client document";

    public static final String INVALID_MONT_RANGE =
            "The number of months is invalid. The value must be between "
                    + BusinessConstants.MIN_LOAN_MONTHS + " and " + BusinessConstants.MAX_LOAN_MONTHS + " months.";
    public static final String INVALID_AMOUNT_RANGE =  "The amount must be between "
            + BusinessConstants.MIN_AMOUNT + " and "  + BusinessConstants.MAX_AMOUNT + " inclusive.";
}
