package com.codejava.course.service;

import com.codejava.course.model.dto.ApiResponse;

import java.util.List;
import java.util.Optional;

/**
 * Base service interface with flexible generics for DTOs and requests.
 *
 * @param <R> Response type
 * @param <C> Create request type
 * @param <U> Update request type
 * @param <F> Filter or search request type
 */
public interface BaseService<R, C, U, F> {

    // Get all with optional filter
    ApiResponse<R> getAll(F filterRequest);

    // Get by ID
    R getById(Long id);

    // Create
    R create(C createRequest);

    // Update
    R update(Long id, U updateRequest);

    // Delete
    void delete(Long id);

    // Optional: additional methods for custom queries
    default ApiResponse<R> customQuery(Object... params) {
        throw new UnsupportedOperationException("Custom query not implemented");
    }
}
