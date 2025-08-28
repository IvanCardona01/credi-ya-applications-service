package co.com.applicationsservice.model.loantype.gateways;

import co.com.applicationsservice.model.loanstatus.LoanStatus;
import co.com.applicationsservice.model.loantype.LoanType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanTypeRepository {
    Flux<LoanType> getAll();
    Mono<LoanType> getById(Long id);
    Mono<Boolean> existsById(Long id);
}
