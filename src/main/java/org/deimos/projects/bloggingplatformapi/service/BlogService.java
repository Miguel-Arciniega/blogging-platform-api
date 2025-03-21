package org.deimos.projects.bloggingplatformapi.service;

import lombok.RequiredArgsConstructor;
import org.deimos.projects.bloggingplatformapi.exceptions.BlogPostNotFoundException;
import org.deimos.projects.bloggingplatformapi.model.BlogPostData;
import org.deimos.projects.bloggingplatformapi.model.BlogPostRequest;
import org.deimos.projects.bloggingplatformapi.model.BlogPostResponse;
import org.deimos.projects.bloggingplatformapi.model.mapper.BlogPostMapper;
import org.deimos.projects.bloggingplatformapi.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service class responsible for handling business logic related to blog entries
 * in the blogging platform. This class primarily communicates with the data layer
 * and provides methods to perform various operations on blog entries.
 * <p>
 * The methods include functionality for creating, retrieving, updating, and
 * deleting blog entries. These methods are designed to be invoked by the
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
     * Creates a new blog entry in the database.
     *
     * @param blogPostRequest The request object containing blog data to be created.
     * @return BlogPostResponse containing the created blog entry details.
     */
    public BlogPostResponse createBlogEntry(final BlogPostRequest blogPostRequest) {

        BlogPostData receivedBlogPostData =
                blogPostMapper.mapRequestToBlogEntryData(blogPostRequest);

        BlogPostData createdBlogPostData =
                blogRepository.save(receivedBlogPostData);

        return blogPostMapper.mapBlogEntryDataToResponse(createdBlogPostData);
    }

    /**
     * Retrieves a blog entry by its ID.
     *
     * @param id The unique identifier of the blog entry.
     * @return BlogPostResponse containing the details of the blog entry.
     * @throws BlogPostNotFoundException if the blog entry is not found.
     */
    public BlogPostResponse getBlogEntryById(final Long id) {

        return blogRepository.findById(id)
                .map(blogPostMapper::mapBlogEntryDataToResponse)
                .orElseThrow(() -> new BlogPostNotFoundException(id));
    }

    /**
     * Retrieves all blog entries stored in the database.
     *
     * @return List of BlogPostResponse containing details of all blog entries.
     */
    public List<BlogPostResponse> getAllBlogEntries() {
        return blogPostMapper.mapToBlogEntryResponses(blogRepository.findAll());
    }

    /**
     * Updates an existing blog entry by its ID.
     *
     * @param id               The unique identifier of the blog entry to be updated.
     * @param blogPostRequest The request object containing updated blog data.
     * @return BlogPostResponse containing the updated blog entry details.
     */
    public BlogPostResponse updateBlogEntry(final BlogPostRequest blogPostRequest, final Long id) {

        BlogPostData newBlogPostData =
                blogPostMapper.mapRequestToBlogEntryData(blogPostRequest);

        BlogPostData updatedPostData = blogRepository.findById(id)
                .map(existingBlogPostData ->
                        blogPostMapper.mapUpdatedBlogData(newBlogPostData, existingBlogPostData))
                .orElseThrow(() -> new BlogPostNotFoundException(id));

        blogRepository.save(updatedPostData);

        return blogPostMapper.mapBlogEntryDataToResponse(updatedPostData);
    }

    /**
     * Deletes a blog entry by its ID.
     *
     * @param id The unique identifier of the blog entry to be deleted.
     */
    public void deleteBlogEntry(final Long id) {
        blogRepository.deleteById(id);
    }
}
