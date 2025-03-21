package org.deimos.projects.bloggingplatformapi.controller;

import lombok.RequiredArgsConstructor;
import org.deimos.projects.bloggingplatformapi.model.BlogPostRequest;
import org.deimos.projects.bloggingplatformapi.model.BlogPostResponse;
import org.deimos.projects.bloggingplatformapi.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to handle blog-related operations in the blogging platform API.
 * Acts as the entry point for HTTP requests related to blogs.
 * This class is annotated with @RestController, indicating that it is a Spring MVC controller.
 */
@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/blogs")
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPostResponse createBlogEntry(@Validated @RequestBody final BlogPostRequest request) {

        return blogService.createBlogEntry(request);
    }

    @GetMapping("/blogs")
    @ResponseStatus(HttpStatus.OK)
    public List<BlogPostResponse> getAllBlogEntries() {

        return blogService.getAllBlogEntries();
    }

    @GetMapping("/blogs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BlogPostResponse getBlogEntryById(@PathVariable final Long id) {

        return blogService.getBlogEntryById(id);
    }

    @PutMapping("/blogs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BlogPostResponse updateBlogEntry(
            @Validated @RequestBody final BlogPostRequest request, @PathVariable final Long id) {

        return blogService.updateBlogEntry(request, id);
    }

    @DeleteMapping("/blogs/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlogEntry(@PathVariable final Long id) {

        blogService.deleteBlogEntry(id);
    }
}
