package com.cmis.cooperative.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, OpaqueTokenAuthenticationConverter opaqueTokenAuthenticationConverter) throws Exception {
        http
                .oauth2ResourceServer(
                        auth -> auth
                                .opaqueToken(  // user-management-service  localhost
                                        opaqueTokenConfigurer -> opaqueTokenConfigurer
                                                .introspectionUri("http://user-management-service:8081/oauth2/introspect")
                                                .introspectionClientCredentials("browser-client", "secret")
                                                .authenticationConverter(opaqueTokenAuthenticationConverter)));

        http.authorizeHttpRequests(
                authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()
                                .requestMatchers(antMatcher("/api-docs/**")).permitAll()
                                .requestMatchers(antMatcher("/api-docs-ui.html")).permitAll()
                                .requestMatchers(antMatcher("/cooperative-service/v3/**")).permitAll()
                                .requestMatchers(antMatcher("/internal/**")).permitAll()
                                .requestMatchers(antMatcher("/products/**")).permitAll()
                                .anyRequest()
                                .authenticated()
        );

        return http.build();
    }

    @Bean
    public OpaqueTokenAuthenticationConverter opaqueTokenAuthenticationConverter() {
        return new CustomOpaqueTokenAuthenticationConverter();
    }

}
