package org.deimos.projects.bloggingplatformapi.utils;

/**
 * This class defines constant values used across the application.
 * Constants are defined as `public static final` fields to ensure
 * immutability and provide a centralized location for reusable static data.
 */
public class Constants {

    public static final String BLOG_ENTRY_NOT_FOUND = "Error: Post with ID {%s} not found.";

    // Constants for endpoint paths
    public static final String POST_ID_PATH = "/{postId}";
    public static final String POST_PATH = "/posts";
}
