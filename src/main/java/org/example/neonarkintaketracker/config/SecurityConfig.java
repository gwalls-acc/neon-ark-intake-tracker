package org.example.neonarkintaketracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplified CLI testing
                .authorizeHttpRequests(auth -> auth
                        // 1. The "Gatekeeper" rule: Only ADMINS can see the user list
                        .requestMatchers("/api/admin/users/**").hasRole("ADMIN")

                        // 2. Common access: Both Users and Admins can see creatures
                        .requestMatchers("/api/creatures/**").hasAnyRole("USER", "ADMIN")

                        // 3. Ensure everyone must be logged in for anything else
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Use Basic Auth for the CLI

        return http.build();
    }
}