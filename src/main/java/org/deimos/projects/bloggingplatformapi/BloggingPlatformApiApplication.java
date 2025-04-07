package org.deimos.projects.bloggingplatformapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.deimos.projects.bloggingplatformapi.repository")
@EnableJpaAuditing
public class BloggingPlatformApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloggingPlatformApiApplication.class, args);
    }

}
