package co.com.applicationsservice.usecase.application;

import co.com.applicationsservice.model.application.Application;
import co.com.applicationsservice.model.application.gateways.ApplicationRepository;
import co.com.applicationsservice.model.loanstatus.LoanStatus;
import co.com.applicationsservice.model.loanstatus.gateways.LoanStatusRepository;
import co.com.applicationsservice.model.loantype.gateways.LoanTypeRepository;
import co.com.applicationsservice.usecase.loanstatus.LoanStatusUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ApplicationUseCase {
    private final ApplicationRepository applicationRepository;

    private final LoanStatusUseCase  loanStatusUseCase;

    public Flux<Application> getAllApplications(){
        return applicationRepository.getAll();
    }

    public Mono<Application> saveApplication(Application application) {
        if (application.getTypeId() == null) {
            return Mono.error(new IllegalArgumentException("LoanType ID is required"));
        }
        return loanStatusUseCase.getDefaultLoanStatus()
                .doOnNext(defaultStatus -> application.setStatusId(defaultStatus.getId()))
                .then(applicationRepository.saveApplication(application))
                .onErrorMap(ex -> new RuntimeException("Failed to save application", ex));
    }
}
