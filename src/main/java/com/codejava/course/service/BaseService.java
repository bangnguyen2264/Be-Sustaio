package com.codejava.course.service;

import com.codejava.course.model.dto.ApiResponse;
import org.springframework.data.domain.Page;

public interface BaseService<D, F> {
    ApiResponse<D> getAll(F filterRequest);
    D getById(Long id);
    D create(D dto);
    D update(Long id, D dto);
    void delete(Long id);
}
