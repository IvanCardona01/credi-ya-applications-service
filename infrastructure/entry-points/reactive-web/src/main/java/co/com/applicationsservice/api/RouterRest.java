package co.com.applicationsservice.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/v1/application"), handler::getAllApplications)
                .andRoute(POST("/api/v1/application"), handler::createApplication)
                .andRoute(GET("/api/v1/loan-status"), handler::getAllLoanStatus)
                .and(route(GET("/api/v1/loan-types"), handler::getAllLoanTypes));
    }
}
