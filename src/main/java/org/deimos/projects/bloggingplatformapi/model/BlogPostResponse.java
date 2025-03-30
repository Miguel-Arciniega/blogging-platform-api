package org.deimos.projects.bloggingplatformapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;


/**
 * Represents the response details of a blog post in the blogging platform.
 * This class is used as a data transfer object (DTO) for returning information
 * about a blog post from the server to the client.
 * <p>
 * The response includes metadata about the blog post, such as its unique identifier,
 * title, content, category, tags, and timestamps for creation and last update.
 * <p>
 * This class is primarily utilized in the service and controller layers of the
 * blogging platform API for presenting blog post data to API consumers.
 */
@Data
@NoArgsConstructor
public class BlogPostResponse {

    private Long id;
    private String title;
    private String content;
    private String category;
    private Set<String> tags;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant updatedAt;
}
