package org.deimos.projects.bloggingplatformapi.service;

import static org.deimos.projects.bloggingplatformapi.utils.Constants.WILD_CARD;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.deimos.projects.bloggingplatformapi.exceptions.BlogPostNotFoundException;
import org.deimos.projects.bloggingplatformapi.model.BlogPostData;
import org.deimos.projects.bloggingplatformapi.model.BlogPostRequest;
import org.deimos.projects.bloggingplatformapi.model.BlogPostResponse;
import org.deimos.projects.bloggingplatformapi.model.mapper.BlogPostMapper;
import org.deimos.projects.bloggingplatformapi.repository.BlogRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


/**
 * Service class responsible for handling business logic related to blog posts
 * in the blogging platform. This class primarily communicates with the data layer
 * and provides methods to perform various operations on blog posts.
 * <p>
 * The methods include functionality for creating, retrieving, updating, and
 * deleting blog posts. These methods are designed to be invoked by the
 * controller layer, serving as the intermediary between the controller and
 * the data access layer.
 * <p>
 * This service is annotated with @Service, designating it as a Spring-managed
 * service component. The @RequiredArgsConstructor annotation is used to generate
 * a constructor for final fields, allowing dependency injection of required services.
 */
@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogPostMapper blogPostMapper;


    /**
     * Creates a new blog post in the database.
     *
     * @param blogPostRequest The request object containing blog data to be created.
     * @return BlogPostResponse containing the created blog post details.
     */
    public BlogPostResponse createBlogPost(final BlogPostRequest blogPostRequest) {

        BlogPostData receivedBlogPostData =
                blogPostMapper.mapRequestToBlogPostData(blogPostRequest);

        BlogPostData createdBlogPostData =
                blogRepository.save(receivedBlogPostData);

        return blogPostMapper.mapBlogPostDataToResponse(createdBlogPostData);
    }

    /**
     * Retrieves a blog post by its ID.
     *
     * @param id The unique identifier of the blog post.
     * @return BlogPostResponse containing the details of the blog post.
     * @throws BlogPostNotFoundException if the blog post is not found.
     */
    public BlogPostResponse getBlogPostById(final Long id) {

        return blogRepository.findById(id)
                .map(blogPostMapper::mapBlogPostDataToResponse)
                .orElseThrow(() -> new BlogPostNotFoundException(id));
    }

    /**
     * Retrieves all blog posts stored in the database.
     * <p>
     * If a search term is provided, it filters the blog posts based on the title,
     * content, or category. If no search term is provided, it returns all blog posts.
     *
     * @return List of BlogPostResponse containing details of all blog posts.
     */
    public List<BlogPostResponse> getBlogPosts(String keyword) {
        if (StringUtils.isNotBlank(keyword)) {
            String searchTerm = WILD_CARD + keyword + WILD_CARD;
            return blogPostMapper.mapToBlogPostList(blogRepository.findBySearchTerm(searchTerm));
        }
        return blogPostMapper.mapToBlogPostList(blogRepository.findAll());
    }

    /**
     * Updates an existing blog post by its ID.
     *
     * @param id               The unique identifier of the blog post to be updated.
     * @param blogPostRequest The request object containing updated blog data.
     * @return BlogPostResponse containing the updated blog post details.
     */
    public BlogPostResponse updateBlogPost(final BlogPostRequest blogPostRequest, final Long id) {

        BlogPostData newBlogPostData =
                blogPostMapper.mapRequestToBlogPostData(blogPostRequest);

        BlogPostData updatedPostData = blogRepository.findById(id)
                .map(existingBlogPostData ->
                        blogPostMapper.mapUpdatedBlogPostData(newBlogPostData, existingBlogPostData))
                .orElseThrow(() -> new BlogPostNotFoundException(id));

        blogRepository.save(updatedPostData);

        return blogPostMapper.mapBlogPostDataToResponse(updatedPostData);
    }

    /**
     * Deletes a blog post by its ID.
     *
     * @param id The unique identifier of the blog post to be deleted.
     */
    public void deleteBlogPost(final Long id) {
        blogRepository.deleteById(id);
    }
}
