package co.com.applicationsservice.api;

import co.com.applicationsservice.api.dto.request.CreateApplicationDTO;
import co.com.applicationsservice.api.dto.response.ApplicationResponseDTO;
import co.com.applicationsservice.api.dto.response.LoanStatusResponseDTO;
import co.com.applicationsservice.api.dto.response.LoanTypeResponseDTO;
import co.com.applicationsservice.api.mapper.LoanStatusDTOMapper;
import co.com.applicationsservice.api.mapper.LoanTypeDTOMapper;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import co.com.applicationsservice.api.mapper.ApplicationDTOMapper;
import co.com.applicationsservice.usecase.application.ApplicationUseCase;
import co.com.applicationsservice.usecase.loanstatus.LoanStatusUseCase;
import co.com.applicationsservice.usecase.loantype.LoanTypeUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

    private final ApplicationUseCase applicationUseCase;
    private final LoanStatusUseCase loanStatusUseCase;
    private final LoanTypeUseCase loanTypeUseCase;

    private final ApplicationDTOMapper applicationDTOMapper;
    private final LoanStatusDTOMapper loanStatusDTOMapper;
    private final LoanTypeDTOMapper loanTypeDTOMapper;


    public Mono<ServerResponse> createApplication(ServerRequest request) {
        log.info("🌐 [API] Creating application");
        
        return request.bodyToMono(CreateApplicationDTO.class)
                .map(applicationDTOMapper::toModel)
                .flatMap(applicationUseCase::saveApplication)
                .map(applicationDTOMapper::toDTO)
                .doOnNext(response -> log.info("🌐 [API] Application created - ID: {}", response.id()))
                .flatMap(responseDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(responseDTO))
                .doOnError(error -> log.error("🌐 [API] Error creating application: {}", error.getMessage()));
    }

    public Mono<ServerResponse> getAllApplications(ServerRequest request) {
        log.info("🌐 [API] Fetching all applications");
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        applicationUseCase.getAllApplications()
                                .map(applicationDTOMapper::toDTO),
                        ApplicationResponseDTO.class
                )
                .doOnError(error -> log.error("🌐 [API] Error fetching applications: {}", error.getMessage()));
    }

    public Mono<ServerResponse> getAllLoanStatus(ServerRequest request) {
        log.info("🌐 [API] Fetching loan statuses");
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        loanStatusUseCase.getAll()
                                .map(loanStatusDTOMapper::toDTO),
                        LoanStatusResponseDTO.class
                )
                .doOnError(error -> log.error("🌐 [API] Error fetching loan statuses: {}", error.getMessage()));
    }

    public Mono<ServerResponse> getAllLoanTypes(ServerRequest request) {
        log.info("🌐 [API] Fetching loan types");
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        loanTypeUseCase.getAll()
                                .map(loanTypeDTOMapper::toDTO),
                        LoanTypeResponseDTO.class
                )
                .doOnError(error -> log.error("🌐 [API] Error fetching loan types: {}", error.getMessage()));
    }
}
