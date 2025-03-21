package org.deimos.projects.bloggingplatformapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Represents a request to create or update a blog entry in the blogging platform.
 * This class is used as a data transfer object (DTO) for accepting input related to
 * a blog entry from the client side.
 * <p>
 * The class contains the details of the blog entry, including its title,
 * content, category, and associated tags. It is utilized in APIs for creating and
 * updating blog entries.
 */
@Data
@NoArgsConstructor
public class BlogPostRequest {

    private String title;
    private String content;
    private String category;
    private Set<String> tags;
}
