package kr.co.olivepay.gateway.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    servers = {@Server(url = "/", description = "Default Server URL")
})
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi memberServiceApi() {
        return GroupedOpenApi.builder()
                .group("member")
                .pathsToMatch("/api/members/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authServiceApi() {
        return GroupedOpenApi.builder()
                 .group("auth")
                 .pathsToMatch("/api/auths/**")
                 .build();
    }

    @Bean
    public GroupedOpenApi commonServiceApi() {
        return GroupedOpenApi.builder()
                 .group("common")
                 .pathsToMatch("/api/commons/**")
                 .build();
    }

    @Bean
    public GroupedOpenApi cardServiceApi() {
        return GroupedOpenApi.builder()
                .group("card")
                .pathsToMatch("/api/cards/**")
                .build();
    }

    @Bean
    public GroupedOpenApi donationServiceApi() {
        return GroupedOpenApi.builder()
                .group("donation")
                .pathsToMatch("/api/donations/**")
                .build();
    }

    @Bean
    public GroupedOpenApi franchiseServiceApi() {
        return GroupedOpenApi.builder()
                 .group("franchise")
                 .pathsToMatch("/api/franchises/**")
                 .build();
    }

    @Bean
    public GroupedOpenApi paymentServiceApi() {
        return GroupedOpenApi.builder()
                 .group("payment")
                 .pathsToMatch("/api/payments/**")
                 .build();
    }

    @Bean
    public GroupedOpenApi fundingServiceApi() {
        return GroupedOpenApi.builder()
                 .group("funding")
                 .pathsToMatch("/api/fundings/**")
                 .build();
    }
}