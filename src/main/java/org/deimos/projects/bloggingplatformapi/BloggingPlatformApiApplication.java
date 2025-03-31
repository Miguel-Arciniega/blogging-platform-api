package org.deimos.projects.bloggingplatformapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories(basePackageClasses = BloggingPlatformApiApplication.class)
@EnableJdbcAuditing
public class BloggingPlatformApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloggingPlatformApiApplication.class, args);
    }

}
