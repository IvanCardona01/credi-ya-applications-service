package co.com.applicationsservice.restconsumer;

import co.com.applicationsservice.model.user.User;
import co.com.applicationsservice.model.user.gateways.UserRepository;
import co.com.applicationsservice.restconsumer.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRestConsumerAdapter implements UserRepository {
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    
    @Value("${adapters.user-service.base-url:http://localhost:8080}")
    private String baseUrl;
    
    @Value("${adapters.user-service.timeout:10s}")
    private Duration timeout;
    
    @Override
    public Mono<User> findByDocument(String clientDocument) {
        String fullUrl = baseUrl + "/api/v1/user/" + clientDocument;
        
        return webClient.get()
                .uri(baseUrl + "/api/v1/user/{documentId}", clientDocument)
                .retrieve()
                .bodyToMono(UserResponseDTO.class)
                .map(dto -> objectMapper.map(dto, User.class))
                .timeout(timeout)
                .onErrorResume(WebClientResponseException.class, this::handleWebClientException)
                .onErrorResume(Exception.class, ex -> Mono.empty());
    }
    
    @Override
    public Mono<Boolean> existsByDocument(String clientDocument) {

        return findByDocument(clientDocument)
                .map(user -> true)
                .defaultIfEmpty(false);
    }

    private Mono<User> handleWebClientException(WebClientResponseException ex) {
        if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
            return Mono.empty();
        }
        
        if (ex.getStatusCode().is5xxServerError()) {
            return Mono.error(new RuntimeException("Error en el servicio de usuarios", ex));
        }
        
        if (ex.getStatusCode().is4xxClientError()) {
            return Mono.empty();
        }

        return Mono.error(ex);
    }
}
