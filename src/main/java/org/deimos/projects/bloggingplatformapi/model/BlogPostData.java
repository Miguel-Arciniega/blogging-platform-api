package org.deimos.projects.bloggingplatformapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;


/**
 * Represents an entry in a blogging platform.
 * This entity includes metadata about the blog post, such as the title, content, category, tags,
 * and audit information regarding its creation and modification.
 * It is intended to serve as a data model for storing and retrieving blog entries.
 * <p>
 * Fields:
 * <li> id: Unique identifier for the blog entry.
 * <li> title: Title of the blog post.
 * <li> content: Main content or body of the blog post.
 * <li> category: Category under which the blog post is filed.
 * <li> tags: List of tags or keywords associated with the blog entry.
 * <li> createdAt: Timestamp when the blog entry was created.
 * <li> createdBy: Identifier of the user who initially created the blog entry.
 * <li> updatedAt: Timestamp of the last modification performed on the blog entry.
 * <li> updatedBy: Identifier of the user who last modified the blog entry.
 */
@Data
@NoArgsConstructor
@Table("BLOG_POST")
public class BlogPostData {

    @Id
    private Long id;
    private String title;
    private String content;
    private String category;
    private String tags;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
}
