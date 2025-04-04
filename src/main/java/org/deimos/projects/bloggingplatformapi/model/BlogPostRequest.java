package org.deimos.projects.bloggingplatformapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Represents a request to create or update a blog post in the blogging platform.
 * This class is used as a data transfer object (DTO) for accepting input related to
 * a blog post from the client side.
 * <p>
 * The class contains the details of the blog post, including its title,
 * content, category, and associated tags. It is utilized in APIs for creating and
 * updating blog posts.
 */
@Data
@NoArgsConstructor
public class BlogPostRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String category;
    @NotEmpty
    private Set<String> tags;
}
