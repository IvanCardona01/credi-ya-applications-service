package co.com.applicationsservice.r2dbc;

import co.com.applicationsservice.model.application.Application;
import co.com.applicationsservice.model.application.gateways.ApplicationRepository;
import co.com.applicationsservice.model.loanstatus.LoanStatus;
import co.com.applicationsservice.model.loantype.LoanType;
import co.com.applicationsservice.r2dbc.entity.ApplicationEntity;
import co.com.applicationsservice.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ApplicationReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Application,
        ApplicationEntity,
        Long,
        ApplicationReactiveRepository
> implements ApplicationRepository {

    private final LoanTypeReactiveRepository loanTypeReactiveRepository;
    private final LoanStatusReactiveRepository loanStatusReactiveRepository;

    public ApplicationReactiveRepositoryAdapter(ApplicationReactiveRepository repository, LoanTypeReactiveRepository loanTypeReactiveRepository, LoanStatusReactiveRepository loanStatusReactiveRepository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Application.class));
        this.loanTypeReactiveRepository = loanTypeReactiveRepository;
        this.loanStatusReactiveRepository = loanStatusReactiveRepository;

    }

    @Override
    public Mono<Application> saveApplication(Application application) {

        return Mono.just(application)
    }



    @Override
    public Flux<Application> getAll() {
        return repository.findAll()
                .flatMap(this::mapToApplicationWithCompletedData);
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
