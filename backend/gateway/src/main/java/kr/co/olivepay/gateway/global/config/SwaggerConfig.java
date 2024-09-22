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
                .pathsToMatch("/members/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authServiceApi() {
        return GroupedOpenApi.builder()
                 .group("auth")
                 .pathsToMatch("/auths/**")
                 .build();
    }

    @Bean
    public GroupedOpenApi commonServiceApi() {
        return GroupedOpenApi.builder()
                 .group("common")
                 .pathsToMatch("/commons/**")
                 .build();
    }

    @Bean
    public GroupedOpenApi cardServiceApi() {
        return GroupedOpenApi.builder()
                .group("card")
                .pathsToMatch("/cards/**")
                .build();
    }

    @Bean
    public GroupedOpenApi donationServiceApi() {
        return GroupedOpenApi.builder()
                .group("donation")
                .pathsToMatch("/donations/**")
                .build();
    }

    @Bean
    public GroupedOpenApi franchiseServiceApi() {
        return GroupedOpenApi.builder()
                 .group("franchise")
                 .pathsToMatch("/franchises/**")
                 .build();
    }

    @Bean
    public GroupedOpenApi paymentServiceApi() {
        return GroupedOpenApi.builder()
                 .group("payment")
                 .pathsToMatch("/payments/**")
                 .build();
    }
}