package org.deimos.projects.bloggingplatformapi.repository;

import org.deimos.projects.bloggingplatformapi.model.BlogPostData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on blog posts.
 * This interface extends Spring Data's JpaRepository, providing standard methods for
 * creating, reading, updating, and deleting entities of type BlogPostData.
 */
@Repository
public interface BlogRepository extends JpaRepository<BlogPostData, Long> {

    @Query("""
            SELECT b FROM BlogPostData b WHERE
            b.title LIKE :searchTerm OR
            b.content LIKE :searchTerm OR
            b.category LIKE :searchTerm
            """)
    List<BlogPostData> findBySearchTerm(@Param("searchTerm") String searchTerm);
}
