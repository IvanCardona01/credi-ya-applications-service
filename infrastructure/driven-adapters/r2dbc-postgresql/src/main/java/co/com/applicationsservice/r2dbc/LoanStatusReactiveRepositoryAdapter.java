package co.com.applicationsservice.r2dbc;

import co.com.applicationsservice.model.loanstatus.LoanStatus;
import co.com.applicationsservice.model.loanstatus.gateways.LoanStatusRepository;
import co.com.applicationsservice.r2dbc.entity.LoanStatusEntity;
import co.com.applicationsservice.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
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
        log.debug("📋 [DB] Fetching all loan statuses");
        return repository.findAll()
                .map(entity -> mapper.map(entity, LoanStatus.class))
                .doOnComplete(() -> log.debug("✅ [DB] Loan statuses retrieved successfully"))
                .doOnError(error -> log.error("❌ [DB] Error fetching loan statuses: {}", error.getMessage()));
    }

    @Override
    public Mono<LoanStatus> getById(Long id) {
        log.debug("🔍 [DB] Finding loan status by ID: {}", id);
        return repository.findById(id)
                .map(entity -> mapper.map(entity, LoanStatus.class))
                .doOnSuccess(result -> {
                    if (result != null) {
                        log.debug("✅ [DB] Loan status found: {}", result.getName());
                    } else {
                        log.debug("⚠️ [DB] Loan status not found for ID: {}", id);
                    }
                })
                .doOnError(error -> log.error("❌ [DB] Error finding loan status ID {}: {}", id, error.getMessage()));
    }
}
