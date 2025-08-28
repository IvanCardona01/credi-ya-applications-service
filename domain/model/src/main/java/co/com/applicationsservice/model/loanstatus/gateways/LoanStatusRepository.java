package co.com.applicationsservice.model.loanstatus.gateways;

import co.com.applicationsservice.model.application.Application;
import co.com.applicationsservice.model.loanstatus.LoanStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanStatusRepository {
    Flux<LoanStatus> getAll();
    Mono<LoanStatus> getById(Long id);
}
