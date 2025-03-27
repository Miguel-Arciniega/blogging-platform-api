package org.deimos.projects.bloggingplatformapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Blog", description = "Blog management APIs")
public class BlogController {

    private final BlogService blogService;

    /**
     * Creates a new blog entry.
     *
     * @param blogPostRequest The request containing the blog data.
     * @return BlogPostResponse containing the created blog entry details.
     */
    @Operation(summary = "Create a new blog post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Blog post created successfully",
            content = @Content(schema = @Schema(implementation = BlogPostResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping(BLOGS_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPostResponse createBlogEntry(
            @Parameter(description = "Blog post to create") 
            @Valid @RequestBody final BlogPostRequest blogPostRequest) {
        return blogService.createBlogEntry(blogPostRequest);
    }

    /**
     * Retrieves all blog entries.
     *
     * @return List of BlogPostResponse containing all blog entries.
     */
    @Operation(summary = "Get all blog posts")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all blog posts",
            content = @Content(schema = @Schema(implementation = BlogPostResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
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
    @Operation(summary = "Get a blog post by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the blog post",
            content = @Content(schema = @Schema(implementation = BlogPostResponse.class))),
        @ApiResponse(responseCode = "404", description = "Blog post not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping(BLOG_BY_ID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public BlogPostResponse getBlogEntryById(
            @Parameter(description = "ID of blog post to retrieve")
            @PathVariable final Long blogId) {
        return blogService.getBlogEntryById(blogId);
    }

    /**
     * Updates an existing blog entry.
     *
     * @param blogId           The unique identifier of the blog entry to update.
     * @param blogPostRequest  The request containing the updated blog data.
     * @return BlogPostResponse containing the updated blog entry details.
     */
    @Operation(summary = "Update an existing blog post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Blog post updated successfully",
            content = @Content(schema = @Schema(implementation = BlogPostResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Blog post not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping(BLOG_BY_ID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public BlogPostResponse updateBlogEntry(
            @Parameter(description = "ID of blog post to update")
            @PathVariable final Long blogId,
            @Parameter(description = "Updated blog post content")
            @Valid @RequestBody final BlogPostRequest blogPostRequest) {
        return blogService.updateBlogEntry(blogPostRequest, blogId);
    }

    /**
     * Deletes a blog entry by its ID.
     *
     * @param blogId The unique identifier of the blog entry to delete.
     */
    @Operation(summary = "Delete a blog post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Blog post deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Blog post not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping(BLOG_BY_ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlogEntry(
            @Parameter(description = "ID of blog post to delete")
            @PathVariable final Long blogId) {
        blogService.deleteBlogEntry(blogId);
    }
}