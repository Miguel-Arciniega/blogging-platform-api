package org.deimos.projects.bloggingplatformapi.model.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BlogPostDataMapperTest {

    private BlogPostMapper blogPostMapper;

    @BeforeEach
    void setUp() {
        blogPostMapper = new BlogPostMapperImpl();
        blogPostMapper.objectMapper = new ObjectMapper(); // Inject ObjectMapper dependency
    }

    @Test
    void testMapStringSetToJSON_ValidSet() {
        // Given
        Set<String> tags = Set.of("Tag1");

        // When
        String json = blogPostMapper.mapStringSetToJSON(tags);

        // Then
        assertEquals("[\"Tag1\"]", json);
    }

    @Test
    void testMapStringSetToJSON_EmptySet() {
        // Given
        Set<String> tags = new HashSet<>();

        // When
        String json = blogPostMapper.mapStringSetToJSON(tags);

        // Then
        assertEquals("[]", json);
    }

    @Test
    void testMapStringSetToJSON_NullSet() {
        // When
        String json = blogPostMapper.mapStringSetToJSON(null);

        // Then
        assertEquals("[]", json);
    }

    @Test
    void testMapJSONStringToSet_ValidJSON() {
        // Given
        String jsonString = "[\"Tag1\",\"Tag2\"]";

        // When
        Set<String> tags = blogPostMapper.mapJSONStringToSet(jsonString);

        // Then
        assertNotNull(tags);
        assertEquals(2, tags.size());
        assertTrue(tags.contains("Tag1"));
        assertTrue(tags.contains("Tag2"));
    }

    @Test
    void testMapJSONStringToSet_InvalidJSON() {
        // Given
        String jsonString = "invalid-json";

        // When
        RuntimeException runtimeException =
                assertThrows(RuntimeException.class, () -> blogPostMapper.mapJSONStringToSet(jsonString));

        // Then
        assertNotNull(runtimeException);
    }

    @Test
    void testMapJSONStringToSet_EmptyString() {
        // Given
        String jsonString = "";

        // When
        Set<String> tags = blogPostMapper.mapJSONStringToSet(jsonString);

        // Then
        assertNotNull(tags);
        assertEquals(0, tags.size());
    }

    @Test
    void testMapJSONStringToSet_NullString() {
        // Given
        String jsonString = null;

        // When
        Set<String> tags = blogPostMapper.mapJSONStringToSet(jsonString);

        // Then
        assertNotNull(tags);
        assertEquals(0, tags.size());
    }
}