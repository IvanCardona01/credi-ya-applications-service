package co.com.applicationsservice.model.application.gateways;

import co.com.applicationsservice.model.application.Application;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApplicationRepository {
    Flux<Application> getAll();
    Mono<Application> saveApplication(Application application);
    Mono<Boolean> existsByClientDocument(String clientDocument);
}
