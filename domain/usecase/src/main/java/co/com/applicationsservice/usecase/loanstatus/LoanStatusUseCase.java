package co.com.applicationsservice.usecase.loanstatus;

import co.com.applicationsservice.model.loanstatus.LoanStatus;
import co.com.applicationsservice.model.loanstatus.gateways.LoanStatusRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanStatusUseCase {
    private final LoanStatusRepository loanStatusRepository;

    public Flux<LoanStatus> getAll() {
        return loanStatusRepository.getAll();
    }

    public Mono<LoanStatus> getDefaultLoanStatus() {
        Long defaultStatusId = 1L;
        return loanStatusRepository.getById(defaultStatusId);
    }

}
