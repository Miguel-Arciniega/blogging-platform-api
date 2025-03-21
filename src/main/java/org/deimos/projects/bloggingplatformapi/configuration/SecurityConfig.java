package org.deimos.projects.bloggingplatformapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

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
