package co.com.applicationsservice.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Applications Service API")
                        .description("Reactive microservice for managing loan applications, statuses, and types")
                        .version("1.0.0"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Development server")))
                .tags(List.of(
                        new Tag()
                                .name("Applications")
                                .description("Operations related to loan applications"),
                        new Tag()
                                .name("Loan Status")
                                .description("Operations to retrieve available loan statuses"),
                        new Tag()
                                .name("Loan Types")
                                .description("Operations to retrieve available loan types")));
    }
}
