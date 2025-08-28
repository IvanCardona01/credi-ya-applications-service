package co.com.applicationsservice.usecase.application;

import co.com.applicationsservice.model.application.Application;
import co.com.applicationsservice.model.application.exceptions.ApplicationBusinessError;
import co.com.applicationsservice.model.application.exceptions.InvalidApplicationData;
import co.com.applicationsservice.model.application.exceptions.UserNotRegisteredError;
import co.com.applicationsservice.model.application.gateways.ApplicationRepository;
import co.com.applicationsservice.model.constants.BusinessConstants;
import co.com.applicationsservice.model.loantype.gateways.LoanTypeRepository;
import co.com.applicationsservice.usecase.constants.UseCaseConstants;
import co.com.applicationsservice.usecase.loanstatus.LoanStatusUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ApplicationUseCase {
    private final ApplicationRepository applicationRepository;

    private final LoanStatusUseCase  loanStatusUseCase;
    private final LoanTypeRepository loanTypeRepository;

    public Flux<Application> getAllApplications(){
        return applicationRepository.getAll();
    }

    public Mono<Application> saveApplication(Application application) {

        return validateNotDuplicate(application)
                .then(validateUserData(application))
                .then(loanStatusUseCase.getDefaultLoanStatus())
                .flatMap(defaultStatus -> {
                    application.setStatusId(defaultStatus.getId());
                    return validateApplication(application);
                })
                .flatMap(applicationRepository::saveApplication);
    }

    private Mono<Void> validateUserData(Application application) {
        return applicationRepository.existsByClientDocument(application.getClientDocument()).flatMap(exist -> {
            if (exist) {
                return Mono.empty();
            }
            return Mono.error(new UserNotRegisteredError("documentNumber: " + application.getClientDocument() + "not found." + UseCaseConstants.USER_NOT_REGISTERED));
        });
    }
    
    private Mono<Void> validateNotDuplicate(Application application) {
        if (application.getClientDocument() == null || application.getClientDocument().isEmpty()) {
            return Mono.error(new InvalidApplicationData(UseCaseConstants.DOCUMENT_REQUIRED));
        }
        
        return applicationRepository.existsByClientDocument(application.getClientDocument())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ApplicationBusinessError(UseCaseConstants.DUPLICATE_APPLICATION));
                    }
                    return Mono.empty();
                });
    }

    private Mono<Application> validateApplication(Application application) {
        return Mono.defer(() -> {
            if (application.getCreditAmount() == null) {
                return Mono.error(new InvalidApplicationData(UseCaseConstants.AMOUNT_REQUIRED));
            }
            if (application.getMonths() == null) {
                return Mono.error(new InvalidApplicationData(UseCaseConstants.MONTH_REQUIRED));
            }
            if (application.getTypeId() == null) {
                return Mono.error(new InvalidApplicationData(UseCaseConstants.LOAN_TYPE_REQUIRED));
            }

            BigDecimal minLoanAmount = new BigDecimal(String.valueOf(BusinessConstants.MIN_AMOUNT));
            BigDecimal maxLoanAmount = new BigDecimal(String.valueOf(BusinessConstants.MAX_AMOUNT));
            if (application.getCreditAmount().compareTo(minLoanAmount) < 0 || application.getCreditAmount().compareTo(maxLoanAmount) > 0 ) {
                return Mono.error(new ApplicationBusinessError(UseCaseConstants.INVALID_AMOUNT_RANGE));
            }

            if (application.getMonths() < BusinessConstants.MIN_LOAN_MONTHS || application.getMonths() > BusinessConstants.MAX_LOAN_MONTHS) {
                return Mono.error(new ApplicationBusinessError(UseCaseConstants.INVALID_MONT_RANGE));
            }

            return loanTypeRepository.existsById(application.getTypeId()).flatMap( exist -> {
                    if (exist) {
                        return Mono.just(application);
                    }
                    return  Mono.error(new ApplicationBusinessError(UseCaseConstants.LOAN_TYPE_UNKNOW));
                }
            );
        });
    }
}
