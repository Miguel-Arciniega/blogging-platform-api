package org.deimos.projects.bloggingplatformapi.service;

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
    void createBlogEntry_Success() {
        // Given
        when(blogPostMapper.mapRequestToBlogEntryData(blogPostRequest)).thenReturn(blogPostData);
        when(blogRepository.save(blogPostData)).thenReturn(blogPostData);
        when(blogPostMapper.mapBlogEntryDataToResponse(blogPostData)).thenReturn(blogPostResponse);

        // When
        BlogPostResponse result = blogService.createBlogEntry(blogPostRequest);

        // Then
        assertNotNull(result);
        verify(blogPostMapper).mapRequestToBlogEntryData(blogPostRequest);
        verify(blogRepository).save(blogPostData);
        verify(blogPostMapper).mapBlogEntryDataToResponse(blogPostData);
    }

    @Test
    void getBlogEntryById_Success() {
        // Given
        when(blogRepository.findById(blogId)).thenReturn(Optional.of(blogPostData));
        when(blogPostMapper.mapBlogEntryDataToResponse(blogPostData)).thenReturn(blogPostResponse);

        // When
        BlogPostResponse result = blogService.getBlogEntryById(blogId);

        // Then
        assertNotNull(result);
        verify(blogRepository).findById(blogId);
        verify(blogPostMapper).mapBlogEntryDataToResponse(blogPostData);
    }

    @Test
    void getBlogEntryById_NotFound() {
        // Given
        when(blogRepository.findById(blogId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BlogPostNotFoundException.class, () -> blogService.getBlogEntryById(blogId));
        verify(blogRepository).findById(blogId);
        verify(blogPostMapper, never()).mapBlogEntryDataToResponse(any());
    }

    @Test
    void getAllBlogEntries_Success() {
        // Given
        List<BlogPostData> blogPostDataList = Arrays.asList(blogPostData);
        List<BlogPostResponse> expectedResponses = Arrays.asList(blogPostResponse);
        when(blogRepository.findAll()).thenReturn(blogPostDataList);
        when(blogPostMapper.mapToBlogEntryResponses(blogPostDataList)).thenReturn(expectedResponses);

        // When
        List<BlogPostResponse> result = blogService.getAllBlogEntries();

        // Then
        assertNotNull(result);
        assertEquals(expectedResponses, result);
        verify(blogRepository).findAll();
        verify(blogPostMapper).mapToBlogEntryResponses(blogPostDataList);
    }

    @Test
    void updateBlogEntry_Success() {
        // Given
        when(blogRepository.findById(blogId)).thenReturn(Optional.of(blogPostData));
        when(blogPostMapper.mapRequestToBlogEntryData(blogPostRequest)).thenReturn(blogPostData);
        when(blogPostMapper.mapUpdatedBlogData(blogPostData, blogPostData)).thenReturn(blogPostData);
        when(blogRepository.save(blogPostData)).thenReturn(blogPostData);
        when(blogPostMapper.mapBlogEntryDataToResponse(blogPostData)).thenReturn(blogPostResponse);

        // When
        BlogPostResponse result = blogService.updateBlogEntry(blogPostRequest, blogId);

        // Then
        assertNotNull(result);
        verify(blogRepository).findById(blogId);
        verify(blogPostMapper).mapRequestToBlogEntryData(blogPostRequest);
        verify(blogPostMapper).mapUpdatedBlogData(blogPostData, blogPostData);
        verify(blogRepository).save(blogPostData);
        verify(blogPostMapper).mapBlogEntryDataToResponse(blogPostData);
    }

    @Test
    void updateBlogEntry_NotFound() {
        // Given
        when(blogRepository.findById(blogId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(BlogPostNotFoundException.class, () -> blogService.updateBlogEntry(blogPostRequest, blogId));
        verify(blogRepository).findById(blogId);
        verify(blogPostMapper, never()).mapUpdatedBlogData(any(), any());
        verify(blogRepository, never()).save(any());
    }

    @Test
    void deleteBlogEntry_Success() {
        // When
        blogService.deleteBlogEntry(blogId);

        // Then
        verify(blogRepository).deleteById(blogId);
    }
} 