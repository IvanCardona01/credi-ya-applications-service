package co.com.applicationsservice.r2dbc;

import co.com.applicationsservice.model.loanstatus.LoanStatus;
import co.com.applicationsservice.model.loanstatus.gateways.LoanStatusRepository;
import co.com.applicationsservice.model.loantype.LoanType;
import co.com.applicationsservice.model.loantype.gateways.LoanTypeRepository;
import co.com.applicationsservice.r2dbc.entity.LoanStatusEntity;
import co.com.applicationsservice.r2dbc.entity.LoanTypeEntity;
import co.com.applicationsservice.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class LoanTypeReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        LoanType,
        LoanTypeEntity,
        Long,
        LoanTypeReactiveRepository
        > implements LoanTypeRepository {
    public LoanTypeReactiveRepositoryAdapter(LoanTypeReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, LoanType.class));
    }

    @Override
    public Flux<LoanType> getAll() {
        return repository.findAll().map(entity -> mapper.map(entity, LoanType.class));
    }

    @Override
    public Mono<LoanType> getById(Long id) {
        return repository.findById(id).map(entity -> mapper.map(entity, LoanType.class));
    }

    @Override
    public Mono<Boolean> existsById(Long id) {
        return repository.existsById(id);
    }
}
