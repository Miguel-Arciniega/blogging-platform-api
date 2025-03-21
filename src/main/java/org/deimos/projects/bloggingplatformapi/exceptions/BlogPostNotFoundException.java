package org.deimos.projects.bloggingplatformapi.exceptions;

import static org.deimos.projects.bloggingplatformapi.utils.Constants.BLOG_ENTRY_NOT_FOUND;

public class BlogPostNotFoundException extends RuntimeException {

    public BlogPostNotFoundException(final Long id) {
        super(BLOG_ENTRY_NOT_FOUND.formatted(id));
    }
}