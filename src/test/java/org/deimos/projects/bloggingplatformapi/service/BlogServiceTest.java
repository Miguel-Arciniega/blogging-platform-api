package org.deimos.projects.bloggingplatformapi.service;

import org.apache.commons.lang3.StringUtils;
import org.deimos.projects.bloggingplatformapi.exceptions.BlogPostNotFoundException;
import org.deimos.projects.bloggingplatformapi.model.BlogPostData;
import org.deimos.projects.bloggingplatformapi.model.BlogPostRequest;
import org.deimos.projects.bloggingplatformapi.model.BlogPostResponse;
import org.deimos.projects.bloggingplatformapi.model.mapper.BlogPostMapper;
import org.deimos.projects.bloggingplatformapi.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private BlogPostMapper blogPostMapper;

    @InjectMocks
    private BlogService blogService;

    private BlogPostRequest blogPostRequest;
    private BlogPostData blogPostData;
    private BlogPostResponse blogPostResponse;
    private Long blogId;

    @BeforeEach
    void setUp() {
        blogId = 1L;
        blogPostRequest = new BlogPostRequest();
        blogPostData = new BlogPostData();
        blogPostResponse = new BlogPostResponse();
    }

    @Test
    void createBlogPost_Success() {
        // Given
        when(blogPostMapper.mapRequestToBlogPostData(blogPostRequest)).thenReturn(blogPostData);
        when(blogRepository.save(blogPostData)).thenReturn(blogPostData);
        when(blogPostMapper.mapBlogPostDataToResponse(blogPostData)).thenReturn(blogPostResponse);

        // When
        BlogPostResponse result = blogService.createBlogPost(blogPostRequest);

        // Then
        assertNotNull(result);
        verify(blogPostMapper).mapRequestToBlogPostData(blogPostRequest);
        verify(blogRepository).save(blogPostData);
        verify(blogPostMapper).mapBlogPostDataToResponse(blogPostData);
    }

    @Test
    void getBlogPostById_Success() {
        // Given
        when(blogRepository.findById(blogId)).thenReturn(Optional.of(blogPostData));
        when(blogPostMapper.mapBlogPostDataToResponse(blogPostData)).thenReturn(blogPostResponse);

        // When
        BlogPostResponse result = blogService.getBlogPostById(blogId);

        // Then
        assertNotNull(result);
        verify(blogRepository).findById(blogId);
        verify(blogPostMapper).mapBlogPostDataToResponse(blogPostData);
    }

    @Test
    void getBlogPostById_NotFound() {
        // Given
        when(blogRepository.findById(blogId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BlogPostNotFoundException.class, () -> blogService.getBlogPostById(blogId));
        verify(blogRepository).findById(blogId);
        verify(blogPostMapper, never()).mapBlogPostDataToResponse(any());
    }

    @Test
    void getAllBlogPosts_Success() {
        // Given
        List<BlogPostData> blogPostDataList = Arrays.asList(blogPostData);
        List<BlogPostResponse> expectedResponses = Arrays.asList(blogPostResponse);
        when(blogRepository.findAll()).thenReturn(blogPostDataList);
        when(blogPostMapper.mapToBlogPostList(blogPostDataList)).thenReturn(expectedResponses);

        // When
        List<BlogPostResponse> result = blogService.getBlogPosts(StringUtils.EMPTY);

        // Then
        assertNotNull(result);
        assertEquals(expectedResponses, result);
        verify(blogRepository).findAll();
        verify(blogPostMapper).mapToBlogPostList(blogPostDataList);
    }

    @Test
    void updateBlogPost_Success() {
        // Given
        when(blogRepository.findById(blogId)).thenReturn(Optional.of(blogPostData));
        when(blogPostMapper.mapRequestToBlogPostData(blogPostRequest)).thenReturn(blogPostData);
        when(blogPostMapper.mapUpdatedBlogPostData(blogPostData, blogPostData)).thenReturn(blogPostData);
        when(blogRepository.save(blogPostData)).thenReturn(blogPostData);
        when(blogPostMapper.mapBlogPostDataToResponse(blogPostData)).thenReturn(blogPostResponse);

        // When
        BlogPostResponse result = blogService.updateBlogPost(blogPostRequest, blogId);

        // Then
        assertNotNull(result);
        verify(blogRepository).findById(blogId);
        verify(blogPostMapper).mapRequestToBlogPostData(blogPostRequest);
        verify(blogPostMapper).mapUpdatedBlogPostData(blogPostData, blogPostData);
        verify(blogRepository).save(blogPostData);
        verify(blogPostMapper).mapBlogPostDataToResponse(blogPostData);
    }

    @Test
    void updateBlogPost_NotFound() {
        // Given
        when(blogRepository.findById(blogId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BlogPostNotFoundException.class, () -> blogService.updateBlogPost(blogPostRequest, blogId));
        verify(blogRepository).findById(blogId);
        verify(blogPostMapper, never()).mapUpdatedBlogPostData(any(), any());
        verify(blogRepository, never()).save(any());
    }

    @Test
    void deleteBlogPost_Success() {
        // When
        blogService.deleteBlogPost(blogId);

        // Then
        verify(blogRepository).deleteById(blogId);
    }
} 