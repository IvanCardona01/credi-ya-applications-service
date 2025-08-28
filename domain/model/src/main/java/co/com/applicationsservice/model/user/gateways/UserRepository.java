package co.com.applicationsservice.model.user.gateways;

import co.com.applicationsservice.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> findByDocument(String clientDocument);
    Mono<Boolean> existsByDocument(String clientDocument);
}
