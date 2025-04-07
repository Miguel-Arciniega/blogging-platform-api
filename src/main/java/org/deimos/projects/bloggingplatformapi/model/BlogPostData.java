package org.deimos.projects.bloggingplatformapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.Instant;

/**
 * Represents an entry in a blogging platform.
 * This entity includes metadata about the blog post, such as the title, content, category, tags,
 * and audit information regarding its creation and modification.
 * It is intended to serve as a data model for storing and retrieving blog posts.
 * <p>
 * Fields:
 * <li> id: Unique identifier for the blog post.
 * <li> title: Title of the blog post.
 * <li> content: Main content or body of the blog post.
 * <li> category: Category under which the blog post is filed.
 * <li> tags: List of tags or keywords associated with the blog post.
 * <li> createdAt: Timestamp when the blog post was created.
 * <li> createdBy: Identifier of the user who initially created the blog post.
 * <li> updatedAt: Timestamp of the last modification performed on the blog post.
 * <li> updatedBy: Identifier of the user who last modified the blog post.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "BLOG_POST")
public class BlogPostData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private String category;

    private String tags;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant currentTimeStamp = Instant.now();
        this.createdAt = currentTimeStamp;
        this.updatedAt = currentTimeStamp;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
