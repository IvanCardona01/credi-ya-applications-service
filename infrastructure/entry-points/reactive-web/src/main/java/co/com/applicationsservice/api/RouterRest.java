package co.com.applicationsservice.api;

import co.com.applicationsservice.api.dto.request.CreateApplicationDTO;
import co.com.applicationsservice.api.dto.response.ApplicationResponseDTO;
import co.com.applicationsservice.api.dto.response.ErrorResponseDTO;
import co.com.applicationsservice.api.dto.response.LoanStatusResponseDTO;
import co.com.applicationsservice.api.dto.response.LoanTypeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/application",
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "getAllApplications",
                    operation = @Operation(
                            operationId = "getAllApplications",
                            tags = {"Applications"},
                            summary = "Get all loan applications",
                            description = "Retrieves all loan applications in the system as a reactive stream",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully retrieved applications",
                                            content = @Content(
                                                    mediaType = MediaType.TEXT_EVENT_STREAM_VALUE,
                                                    schema = @Schema(implementation = ApplicationResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "405",
                                            description = "Method not allowed",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                                            )
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/application",
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "createApplication",
                    operation = @Operation(
                            operationId = "createApplication",
                            tags = {"Applications"},
                            summary = "Create a new loan application",
                            description = "Creates a new loan application with the provided data",
                            requestBody = @RequestBody(
                                    description = "Application data to create",
                                    required = true,
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CreateApplicationDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Application created successfully",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ApplicationResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid application data or business rule violation",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDTO.class,
                                                            example = "{\"code\":\"VALIDATION_ERROR\",\"message\":\"Client document is required\",\"timestamp\":\"2024-01-15T10:30:00\",\"path\":\"/api/v1/application\"}")
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "User not registered in the system",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDTO.class,
                                                            example = "{\"code\":\"USER_NOT_FOUND\",\"message\":\"User with document 12345678 is not registered\",\"timestamp\":\"2024-01-15T10:30:00\",\"path\":\"/api/v1/application\"}")
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                                            )
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/loan-status",
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "getAllLoanStatus",
                    operation = @Operation(
                            operationId = "getAllLoanStatus",
                            tags = {"Loan Status"},
                            summary = "Get all loan statuses",
                            description = "Retrieves all available loan statuses as a reactive stream",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully retrieved loan statuses",
                                            content = @Content(
                                                    mediaType = MediaType.TEXT_EVENT_STREAM_VALUE,
                                                    schema = @Schema(implementation = LoanStatusResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "405",
                                            description = "Method not allowed",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                                            )
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/loan-types",
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "getAllLoanTypes",
                    operation = @Operation(
                            operationId = "getAllLoanTypes",
                            tags = {"Loan Types"},
                            summary = "Get all loan types",
                            description = "Retrieves all available loan types as a reactive stream",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successfully retrieved loan types",
                                            content = @Content(
                                                    mediaType = MediaType.TEXT_EVENT_STREAM_VALUE,
                                                    schema = @Schema(implementation = LoanTypeResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "405",
                                            description = "Method not allowed",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                                            )
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/v1/application"), handler::getAllApplications)
                .andRoute(POST("/api/v1/application"), handler::createApplication)
                .andRoute(GET("/api/v1/loan-status"), handler::getAllLoanStatus)
                .and(route(GET("/api/v1/loan-types"), handler::getAllLoanTypes));
    }
}
