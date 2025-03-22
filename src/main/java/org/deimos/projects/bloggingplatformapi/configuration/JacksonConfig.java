package org.deimos.projects.bloggingplatformapi.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing the Jackson {@link ObjectMapper}.
 * <p>
 * This configuration registers the {@link JavaTimeModule} for better handling of Java 8 Date and Time API
 * objects during serialization and deserialization. Additionally, it modifies the default serialization
 * behavior by disabling certain features:
 * <p>
 * <ol>
 * <li>{@link SerializationFeature#WRITE_DATES_AS_TIMESTAMPS} - Ensures that date and time objects
 *    are serialized in an ISO-8601 string format instead of timestamps.
 * <li>{@link SerializationFeature#WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS} - Avoids using nanoseconds
 *    for date and time serialization, ensuring compatibility.
 * <p>
 * The customized {@link ObjectMapper} can be used as a Bean in other parts of the application where
 * JSON processing is needed.
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Use ISO-8601
        mapper.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS); // Avoid nanoseconds

        return mapper;
    }
}