package org.deimos.projects.bloggingplatformapi.model.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.deimos.projects.bloggingplatformapi.model.BlogPostData;
import org.deimos.projects.bloggingplatformapi.model.BlogPostRequest;
import org.deimos.projects.bloggingplatformapi.model.BlogPostResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

/**
 * BlogPostMapper is an abstract class responsible for mapping between different layers of the
 * application related to the BlogPost entity. It extends the capabilities of the MapStruct library
 * to perform object mapping while integrating custom logic for specific fields.
 * <p>
 * The mapper handles conversion between the following representations:
 * <ul>
 * <li> BlogPostRequest: Input DTO used by client-side requests.
 * <li> BlogPostData: Internal data model for storing blog-related information.
 * <li> BlogPostResponse: Output DTO used to return data back to the client.
 * </ul>
 * <p>
 * Key Responsibilities:
 * <ul>
 * <li> Convert BlogPostRequest to BlogPostData while excluding certain fields (e.g., id, createdAt, updatedAt)
 *   and transforming tags into a JSON string using a qualified method.
 * <li> Convert BlogPostData to BlogPostResponse, transforming JSON-encoded tags back into a Set<String>
 *   using a qualified method.
 * <li> Merge updates from a new BlogPostData into an existing BlogPostData instance during updates, ensuring
 *   immutability for specific fields such as createdAt and updatedAt.
 * <li> Map collections of BlogPostData to a list of BlogPostResponse using stream processing.
 * </ul>
 * <p>
 * Custom Logic:
 * <ul>
 * <li> The mapStringSetToJSON method serializes a Set<String> into a JSON string format.
 * <li> The mapJSONStringToSet method deserializes a JSON string into a Set<String>.
 * <li> These custom methods use an ObjectMapper for JSON handling, they are used by mapstruct
 * </ul>
 */
@Mapper(componentModel = "spring")
public abstract class BlogPostMapper {

    @Autowired
    protected ObjectMapper objectMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract BlogPostData mapRequestToBlogPostData(final BlogPostRequest blogPostRequest);

    public abstract BlogPostResponse mapBlogPostDataToResponse(final BlogPostData blogPostData);

    @Mapping(source = "oldPost.id", target = "id")
    @Mapping(source = "newPost.title", target = "title")
    @Mapping(source = "newPost.content", target = "content")
    @Mapping(source = "newPost.category", target = "category")
    @Mapping(source = "newPost.tags", target = "tags")
    @Mapping(source = "oldPost.createdAt", target = "createdAt")
    @Mapping(source = "oldPost.updatedAt", target = "updatedAt")
    public abstract BlogPostData mapUpdatedBlogPostData(final BlogPostData newPost, final BlogPostData oldPost);

    protected String mapStringSetToJSON(final Set<String> stringSet) {
        if (stringSet == null) {
            return "[]";
        }
        
        try {
            return objectMapper.writeValueAsString(stringSet);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize tags to JSON", e);
        }
    }

    protected Set<String> mapJSONStringToSet(final String jsonString) {
        if (jsonString == null || jsonString.isBlank()) {
            return Collections.emptySet();
        }

        try {
            TypeReference<Set<String>> typeRef = new TypeReference<>() {};
            return objectMapper.readValue(jsonString, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON string to Set<String>: " + jsonString, e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while converting JSON string to Set<String>", e);
        }
    }

    public List<BlogPostResponse> mapToBlogPostList(final Iterable<BlogPostData> blogEntries) {
        return StreamSupport.stream(blogEntries.spliterator(), false)
                .map(this::mapBlogPostDataToResponse)
                .toList();
    }
}
