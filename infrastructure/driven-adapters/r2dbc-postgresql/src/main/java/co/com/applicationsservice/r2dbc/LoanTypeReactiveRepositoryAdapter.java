package co.com.applicationsservice.r2dbc;

import co.com.applicationsservice.model.loanstatus.LoanStatus;
import co.com.applicationsservice.model.loanstatus.gateways.LoanStatusRepository;
import co.com.applicationsservice.model.loantype.LoanType;
import co.com.applicationsservice.model.loantype.gateways.LoanTypeRepository;
import co.com.applicationsservice.r2dbc.entity.LoanStatusEntity;
import co.com.applicationsservice.r2dbc.entity.LoanTypeEntity;
import co.com.applicationsservice.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
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
        log.debug("📋 [DB] Fetching all loan types");
        return repository.findAll()
                .map(entity -> mapper.map(entity, LoanType.class))
                .doOnComplete(() -> log.debug("✅ [DB] Loan types retrieved successfully"))
                .doOnError(error -> log.error("❌ [DB] Error fetching loan types: {}", error.getMessage()));
    }

    @Override
    public Mono<LoanType> getById(Long id) {
        log.debug("🔍 [DB] Finding loan type by ID: {}", id);
        return repository.findById(id)
                .map(entity -> mapper.map(entity, LoanType.class))
                .doOnSuccess(result -> {
                    if (result != null) {
                        log.debug("✅ [DB] Loan type found: {}", result.getName());
                    } else {
                        log.debug("⚠️ [DB] Loan type not found for ID: {}", id);
                    }
                })
                .doOnError(error -> log.error("❌ [DB] Error finding loan type ID {}: {}", id, error.getMessage()));
    }

    @Override
    public Mono<Boolean> existsById(Long id) {
        log.debug("🔍 [DB] Checking loan type exists by ID: {}", id);
        return repository.existsById(id)
                .doOnSuccess(exists -> log.debug("✅ [DB] Loan type ID {} exists: {}", id, exists))
                .doOnError(error -> log.error("❌ [DB] Error checking loan type ID {}: {}", id, error.getMessage()));
    }
}
