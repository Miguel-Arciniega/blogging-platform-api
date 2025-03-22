package org.deimos.projects.bloggingplatformapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Configuration class for setting up the security configuration of the application.
 * <p>
 * This class configures security settings using Spring Security's fluent API.
 * The primary focus is to define the behavior of HTTP security interactions, including
 * cross-site request forgery (CSRF) handling, request authorization, and basic authentication.
 * <p>
 * Features configured:
 * <ol>
 * <li>CSRF protection is disabled to allow non-browser clients (e.g., Postman) to interact with the application.
 * <li> All HTTP requests are permitted without authentication, allowing unrestricted access.
 * <li> HTTP Basic Authentication is enabled as a default mechanism.
 * <p>
 * By using the defined bean, the application utilizes the SecurityFilterChain to enforce these security policies.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for non-browser clients like Postman
                .csrf(AbstractHttpConfigurer::disable)
                // Permit all requests without authentication
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                // Optionally, if you want to use basic auth, you can remove the line above
                // and configure basic authentication as needed.
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
