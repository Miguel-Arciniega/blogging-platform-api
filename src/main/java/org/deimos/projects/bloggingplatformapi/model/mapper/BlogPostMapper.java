package org.deimos.projects.bloggingplatformapi.model.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.deimos.projects.bloggingplatformapi.model.BlogPostData;
import org.deimos.projects.bloggingplatformapi.model.BlogPostResponse;
import org.deimos.projects.bloggingplatformapi.model.BlogPostRequest;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Mapper(componentModel = "spring")
public abstract class BlogPostMapper {

    @Autowired
    protected ObjectMapper objectMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "tags", target = "tags", qualifiedByName = "mapStringSetToJSON")
    public abstract BlogPostData mapRequestToBlogEntryData(final BlogPostRequest blogPostRequest);

    @Mapping(source = "tags", target = "tags", qualifiedByName = "mapJSONStringToSet")
    public abstract BlogPostResponse mapBlogEntryDataToResponse(final BlogPostData blogPostData);

    @Mapping(source = "oldPost.id", target = "id")
    @Mapping(source = "newPost.title", target = "title")
    @Mapping(source = "newPost.content", target = "content")
    @Mapping(source = "newPost.category", target = "category")
    @Mapping(source = "newPost.tags", target = "tags")
    @Mapping(source = "oldPost.createdAt", target = "createdAt")
    @Mapping(source = "oldPost.updatedAt", target = "updatedAt")
    public abstract BlogPostData mapUpdatedBlogData(final BlogPostData newPost, final BlogPostData oldPost);

    public List<BlogPostResponse> mapToBlogEntryResponses(final Iterable<BlogPostData> blogEntries) {
        return StreamSupport.stream(blogEntries.spliterator(), false)
                .map(this::mapBlogEntryDataToResponse)
                .collect(Collectors.toList());
    }

    @Named("mapStringSetToJSON")
    protected String mapStringSetToJSON(final Set<String> stringSet) {
        if (stringSet == null || stringSet.isEmpty()) {
            return "[]";
        }
        try {
            return objectMapper.writeValueAsString(stringSet);
        } catch (Exception e) {
            throw new RuntimeException("Error while converting String Set to JSON", e);
        }
    }

    @Named("mapJSONStringToSet")
    protected Set<String> mapJSONStringToSet(final String jsonString) {
        if(jsonString ==null||jsonString.isEmpty()) {
            return new HashSet<>();
        }
        try {
            return objectMapper.readValue(
                    jsonString, objectMapper.getTypeFactory().constructCollectionType(Set.class, String.class));
        } catch(Exception e) {
            throw new RuntimeException("Error while converting JSON String to Set", e);
        }
    }
}
