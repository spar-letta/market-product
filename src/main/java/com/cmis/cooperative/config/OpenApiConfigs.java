package com.cmis.cooperative.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition
@Configuration
public class OpenApiConfigs {
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("member-service doc")
//                        .version("1.0")
//                        .description("Hospital Management")
//                        .contact(new Contact()
//                                .name("javenock")
//                                .email("simiyuenock1990@gmail.com")
//                                .url("https://javenock-portifolio.netlify.app")));
//    }
@Bean
public OpenAPI userOpenAPI(
        @Value("coop") String serviceTitle,
        @Value("3") String serviceVersion,
        @Value("") String url) {
    return new OpenAPI()
            .servers(List.of(new Server().url(url)))
            .info(new Info().title(serviceTitle).version(serviceVersion));
}
}
