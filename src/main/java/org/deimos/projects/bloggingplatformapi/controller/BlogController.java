package org.deimos.projects.bloggingplatformapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.deimos.projects.bloggingplatformapi.model.BlogPostRequest;
import org.deimos.projects.bloggingplatformapi.model.BlogPostResponse;
import org.deimos.projects.bloggingplatformapi.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.deimos.projects.bloggingplatformapi.utils.Constants.BLOGS_PATH;
import static org.deimos.projects.bloggingplatformapi.utils.Constants.BLOG_BY_ID_PATH;

/**
 * Handles blog-related operations in the blogging platform API.
 * Provides endpoints for creating, retrieving, updating, and deleting blogs.
 */
@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    /**
     * Creates a new blog entry.
     *
     * @param blogPostRequest The request containing the blog data.
     * @return BlogPostResponse containing the created blog entry details.
     */
    @PostMapping(BLOGS_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPostResponse createBlogEntry(@Valid @RequestBody final BlogPostRequest blogPostRequest) {
        return blogService.createBlogEntry(blogPostRequest);
    }

    /**
     * Retrieves all blog entries.
     *
     * @return List of BlogPostResponse containing all blog entries.
     */
    @GetMapping(BLOGS_PATH)
    @ResponseStatus(HttpStatus.OK)
    public List<BlogPostResponse> getAllBlogEntries() {
        return blogService.getAllBlogEntries();
    }

    /**
     * Retrieves a blog entry by its ID.
     *
     * @param blogId The unique identifier of the blog entry.
     * @return BlogPostResponse containing the blog entry details.
     */
    @GetMapping(BLOG_BY_ID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public BlogPostResponse getBlogEntryById(@PathVariable final Long blogId) {
        return blogService.getBlogEntryById(blogId);
    }

    /**
     * Updates an existing blog entry.
     *
     * @param blogId           The unique identifier of the blog entry to update.
     * @param blogPostRequest  The request containing the updated blog data.
     * @return BlogPostResponse containing the updated blog entry details.
     */
    @PutMapping(BLOG_BY_ID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public BlogPostResponse updateBlogEntry(
            @PathVariable final Long blogId,
            @Valid @RequestBody final BlogPostRequest blogPostRequest) {
        return blogService.updateBlogEntry(blogPostRequest, blogId);
    }

    /**
     * Deletes a blog entry by its ID.
     *
     * @param blogId The unique identifier of the blog entry to delete.
     */
    @DeleteMapping(BLOG_BY_ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlogEntry(@PathVariable final Long blogId) {
        blogService.deleteBlogEntry(blogId);
    }
}