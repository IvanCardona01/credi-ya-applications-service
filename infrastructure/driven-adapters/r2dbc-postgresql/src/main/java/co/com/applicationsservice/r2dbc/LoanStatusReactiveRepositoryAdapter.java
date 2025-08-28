package co.com.applicationsservice.r2dbc;

import co.com.applicationsservice.model.loanstatus.LoanStatus;
import co.com.applicationsservice.model.loanstatus.gateways.LoanStatusRepository;
import co.com.applicationsservice.r2dbc.entity.LoanStatusEntity;
import co.com.applicationsservice.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public class LoanStatusReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        LoanStatus,
        LoanStatusEntity,
        Long,
        LoanStatusReactiveRepository
        > implements LoanStatusRepository {

    public LoanStatusReactiveRepositoryAdapter(LoanStatusReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, LoanStatus.class));
    }

    @Override
    public Flux<LoanStatus> getAll() {
        return repository.findAll().map(entity -> mapper.map(entity, LoanStatus.class));
    }

    @Override
    public Mono<LoanStatus> getById(Long id) {
        return repository.findById(id).map(entity -> mapper.map(entity, LoanStatus.class));
    }
}
