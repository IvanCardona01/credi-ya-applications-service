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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


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
        return request.bodyToMono(CreateApplicationDTO.class)
                .map(applicationDTOMapper::toModel)
                .flatMap(applicationUseCase::saveApplication)
                .flatMap(savedApplication ->  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(applicationDTOMapper.toDTO(savedApplication))
                );
    }

    public Mono<ServerResponse> getAllApplications(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        applicationUseCase.getAllApplications()
                                .map(applicationDTOMapper::toDTO),
                        ApplicationResponseDTO.class
                );
    }

    public Mono<ServerResponse> getAllLoanStatus(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        loanStatusUseCase.getAll()
                                .map(loanStatusDTOMapper::toDTO),
                        LoanStatusResponseDTO.class
                );
    }

    public Mono<ServerResponse> getAllLoanTypes(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        loanTypeUseCase.getAll()
                                .map(loanTypeDTOMapper::toDTO),
                        LoanTypeResponseDTO.class
                );
    }
}
