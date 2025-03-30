package org.deimos.projects.bloggingplatformapi.repository;

import org.deimos.projects.bloggingplatformapi.model.BlogPostData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for performing CRUD operations on blog posts.
 * This interface extends Spring Data's CrudRepository, providing standard methods for
 * creating, reading, updating, and deleting entities of type BlogEntry.
 * <p>
 * The generic parameters specify the entity type as BlogEntry and the ID type as Long.
 * Spring Data generates the implementation for this interface at runtime, enabling easy
 * access to blog post persistence without the need for boilerplate code.
 */
@Repository
public interface BlogRepository extends CrudRepository<BlogPostData, Long> {}
