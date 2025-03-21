package org.deimos.projects.bloggingplatformapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a standardized response structure for API responses.
 * This class is utilized as a data transfer object (DTO) to provide feedback to
 * clients invoking the API endpoints.
 * <p>
 * The response includes two primary attributes:
 * - code: The HTTP response status code indicating the outcome of the operation.
 * - message: A descriptive message providing additional details about the response.
 * <p>
 * This class is particularly useful in constructing error responses within
 * exception handling mechanisms or in providing success feedback.
 */
@Data
@AllArgsConstructor
public class Response {

    private Integer code;
    private String message;
}
