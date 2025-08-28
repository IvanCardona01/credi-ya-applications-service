package co.com.applicationsservice.usecase.application;

import co.com.applicationsservice.model.application.Application;
import co.com.applicationsservice.model.application.gateways.ApplicationRepository;
import co.com.applicationsservice.model.loanstatus.gateways.LoanStatusRepository;
import co.com.applicationsservice.model.loantype.gateways.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ApplicationUseCase {
    private final ApplicationRepository applicationRepository;
    private final LoanStatusRepository loanStatusRepository;
    private final LoanTypeRepository loanTypeRepository;

    public Flux<Application> getAllApplications(){
        return applicationRepository.getAll();
    }

    public Mono<Application> saveApplication(Application application) {
        //TODO: Apply Business Logic for this Use Case and sabe if is valid
        return Mono.error(new RuntimeException("Unkown error"));
    }
}
