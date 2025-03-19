package com.example.library.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:5500"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**","/swagger-ui/**","/v3/api-docs/**","/swagger-resources/**","/webjars/**").permitAll()// Auth endpointləri açıqdır
                        .requestMatchers(HttpMethod.GET,"/api/books").permitAll()
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/books").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/books/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/books/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/api/books/{id}/update-copies").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/rentals/my-books").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/api/rentals/rent").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/api/rentals","/api/rentals/{id}").hasRole("ADMIN")
                        .anyRequest().authenticated() // Qalan requestlər authentication tələb edir
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
