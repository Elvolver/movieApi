package com.movieflix.movieApi.auth.config;

import com.movieflix.movieApi.auth.converter.KCRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KCRoleConverter());

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/video/*")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/video/poster/*")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/*")).permitAll()
                        .anyRequest().permitAll()
                );

        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(
                        jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter)
                )
        );

        return http.build();
    }
}