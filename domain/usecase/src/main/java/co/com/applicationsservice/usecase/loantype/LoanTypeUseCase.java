package co.com.applicationsservice.usecase.loantype;

import co.com.applicationsservice.model.loantype.LoanType;
import co.com.applicationsservice.model.loantype.gateways.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanTypeUseCase {
    private final LoanTypeRepository loanTypeRepository;

    public Flux<LoanType> getAll() {
        return loanTypeRepository.getAll();
    }

    public Mono<LoanType> getById(Long id) {
        return loanTypeRepository.getById(id);
    }

    public Mono<Boolean> existsById(Long id) {
        return loanTypeRepository.existsById(id);
    }
}
