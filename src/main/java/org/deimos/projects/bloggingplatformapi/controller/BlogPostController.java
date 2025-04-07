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

import static org.deimos.projects.bloggingplatformapi.utils.Constants.*;

/**
 * Handles post-related operations in the blogging platform API.
 * Provides endpoints for creating, retrieving, updating, and deleting posts.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(POST_PATH)
@Tag(name = "Post API", description = "Blog post management APIs")
public class BlogPostController {

    private final BlogService blogPostService;

    /**
     * Creates a new blog post.
     *
     * @param blogPostRequest The request containing the blog data.
     * @return BlogPostResponse containing the created blog post details.
     */
    @Operation(summary = "Create a new blog post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Blog post created successfully", 
                        content = @Content(schema = @Schema(implementation = BlogPostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPostResponse createPost(
            @Parameter(description = "Blog post to create") @Valid @RequestBody final BlogPostRequest blogPostRequest) {
        return blogPostService.createBlogPost(blogPostRequest);
    }

    /**
     * Retrieves all blog posts.
     *
     * @return List of BlogPostResponse containing all blog posts.
     */
    @Operation(summary = "Get blog posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all blog posts", 
                        content = @Content(schema = @Schema(implementation = BlogPostResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BlogPostResponse> getBlogPosts(
            @Parameter(description = "Optional search term to filter blog posts")
            @RequestParam(value = "term", required = false) final String searchTerm) {
        return blogPostService.getBlogPosts(searchTerm);
    }

    /**
     * Retrieves a blog post by its ID.
     *
     * @param postId The unique identifier of the blog post.
     * @return BlogPostResponse containing the blog post details.
     */
    @Operation(summary = "Get a blog post by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the blog post", 
                        content = @Content(schema = @Schema(implementation = BlogPostResponse.class))),
            @ApiResponse(responseCode = "404", description = "Blog post not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping(POST_ID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public BlogPostResponse getBlogPostById(
            @Parameter(description = "ID of blog post to retrieve") @PathVariable("postId") final Long postId) {
        return blogPostService.getBlogPostById(postId);
    }

    /**
     * Updates an existing blog post.
     *
     * @param postId          The unique identifier of the blog post to update.
     * @param blogPostRequest The request containing the updated blog post data.
     * @return BlogPostResponse containing the updated blog post details.
     */
    @Operation(summary = "Update an existing blog post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog post updated successfully", 
                        content = @Content(schema = @Schema(implementation = BlogPostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Blog post not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping(POST_ID_PATH)
    @ResponseStatus(HttpStatus.OK)
    public BlogPostResponse updateBlogPost(
            @Parameter(description = "ID of blog post to update") @PathVariable("postId") final Long postId,
            @Parameter(description = "Updated blog post content") @Valid @RequestBody final BlogPostRequest blogPostRequest) {
        return blogPostService.updateBlogPost(blogPostRequest, postId);
    }

    /**
     * Deletes a blog post by its ID.
     *
     * @param postId The unique identifier of the blog post to delete.
     */
    @Operation(summary = "Delete a blog post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Blog post deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Blog post not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping(POST_ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlogPost(
            @Parameter(description = "ID of blog post to delete") @PathVariable("postId") final Long postId) {
        blogPostService.deleteBlogPost(postId);
    }
}