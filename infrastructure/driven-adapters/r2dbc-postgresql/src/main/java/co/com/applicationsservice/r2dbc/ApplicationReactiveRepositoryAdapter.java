package co.com.applicationsservice.r2dbc;

import co.com.applicationsservice.model.application.Application;
import co.com.applicationsservice.model.application.gateways.ApplicationRepository;
import co.com.applicationsservice.model.loanstatus.LoanStatus;
import co.com.applicationsservice.model.loantype.LoanType;
import co.com.applicationsservice.r2dbc.entity.ApplicationEntity;
import co.com.applicationsservice.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class ApplicationReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Application,
        ApplicationEntity,
        Long,
        ApplicationReactiveRepository
> implements ApplicationRepository {

    private final LoanTypeReactiveRepository loanTypeReactiveRepository;
    private final LoanStatusReactiveRepository loanStatusReactiveRepository;
    private final TransactionalOperator transactionalOperator;

    public ApplicationReactiveRepositoryAdapter(ApplicationReactiveRepository repository, 
                                              LoanTypeReactiveRepository loanTypeReactiveRepository, 
                                              LoanStatusReactiveRepository loanStatusReactiveRepository, 
                                              ObjectMapper mapper,
                                              TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, Application.class));
        this.loanTypeReactiveRepository = loanTypeReactiveRepository;
        this.loanStatusReactiveRepository = loanStatusReactiveRepository;
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<Application> saveApplication(Application application) {
        log.debug("💾 [DB] Saving application for client: {}", application.getClientDocument());
        return saveApplicationInternal(application)
                .as(transactionalOperator::transactional)
                .doOnSuccess(saved -> log.debug("✅ [DB] Application saved successfully with ID: {}", saved.getId()))
                .doOnError(error -> log.error("❌ [DB] Error saving application for client {}: {}", 
                        application.getClientDocument(), error.getMessage()));
    }
    
    private Mono<Application> saveApplicationInternal(Application application) {
        return validateReferences(application)
                .then(Mono.fromCallable(() -> {
                    ApplicationEntity entity = mapper.map(application, ApplicationEntity.class);
                    entity.setTypeId(application.getTypeId());
                    entity.setStatusId(application.getStatusId());
                    return entity;
                }))
                .flatMap(entity -> repository.save(entity))
                .flatMap(this::buildCompleteApplicationFromEntity);
    }

    private Mono<Void> validateReferences(Application application) {
        log.debug("🔍 [DB] Validating references - Type ID: {}, Status ID: {}", 
                application.getTypeId(), application.getStatusId());
        return Mono.when(
                validateTypeExists(application.getTypeId()),
                validateStatusExists(application.getStatusId())
        ).doOnSuccess(v -> log.debug("✅ [DB] References validation completed"))
         .doOnError(error -> log.error("❌ [DB] Reference validation failed: {}", error.getMessage()));
    }

    private Mono<Void> validateTypeExists(Long typeId) {
        return typeId != null
                ? loanTypeReactiveRepository.existsById(typeId)
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new RuntimeException("LoanType with ID " + typeId + " not found")))
                .then()
                : Mono.empty();
    }

    private Mono<Void> validateStatusExists(Long statusId) {
        return statusId != null
                ? loanStatusReactiveRepository.existsById(statusId)
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new RuntimeException("LoanStatus with ID " + statusId + " not found")))
                .then()
                : Mono.empty();
    }

    private Mono<Application> buildCompleteApplicationFromEntity(ApplicationEntity entity) {
        Application application = mapper.map(entity, Application.class);

        return Mono.when(
                validateAndSetType(entity, application),
                validateAndSetStatus(entity, application)
        ).thenReturn(application);
    }

    @Override
    public Flux<Application> getAll() {
        log.debug("📋 [DB] Fetching all applications with complete data");
        return repository.findAll()
                .flatMap(this::mapToApplicationWithCompletedData)
                .doOnComplete(() -> log.debug("✅ [DB] Applications retrieved successfully"))
                .doOnError(error -> log.error("❌ [DB] Error fetching applications: {}", error.getMessage()));
    }

    @Override
    public Mono<Boolean> existsByClientDocument(String clientDocument) {
        log.debug("🔍 [DB] Checking application exists for client: {}", clientDocument);
        return repository.existsByClientDocument(clientDocument)
                .doOnSuccess(exists -> log.debug("✅ [DB] Client {} has application: {}", clientDocument, exists))
                .doOnError(error -> log.error("❌ [DB] Error checking application for client {}: {}", 
                        clientDocument, error.getMessage()));
    }

    private Mono<Application> mapToApplicationWithCompletedData(ApplicationEntity entity) {
        Application application = mapper.map(entity, Application.class);

        return Mono.when(
                validateAndSetType(entity, application),
                validateAndSetStatus(entity, application)
        ).thenReturn(application);
    }

    private Mono<Void> validateAndSetType(ApplicationEntity entity, Application application) {
        return entity.getTypeId() != null
                ? loanTypeReactiveRepository.findById(entity.getTypeId())
                .map(loanTypeEntity -> mapper.map(loanTypeEntity, LoanType.class))
                .doOnNext(application::setLoanType)
                .then()
                : Mono.empty();
    }

    private Mono<Void> validateAndSetStatus(ApplicationEntity entity, Application application) {
        return entity.getStatusId() != null
                ? loanStatusReactiveRepository.findById(entity.getStatusId())
                .map(statusEntity -> mapper.map(statusEntity, LoanStatus.class))
                .doOnNext(application::setLoanStatus)
                .then()
                : Mono.empty();
    }
}
