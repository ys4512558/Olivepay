package kr.co.olivepay.gateway.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    servers = {@Server(url = "/", description = "Default Server URL")
})
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi commonServiceApi() {
        return GroupedOpenApi.builder()
                 .group("common")
                 .pathsToMatch("/common/**")
                 .build();
    }

    @Bean
    public GroupedOpenApi cardServiceApi() {
        return GroupedOpenApi.builder()
                .group("card")
                .pathsToMatch("/card/**")
                .build();
    }

    @Bean
    public GroupedOpenApi donationServiceApi() {
        return GroupedOpenApi.builder()
                .group("donation")
                .pathsToMatch("/donation/**")
                .build();
    }
}