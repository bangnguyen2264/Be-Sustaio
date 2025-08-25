package com.codejava.course.model.request;

import com.codejava.course.model.constant.Sort;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Base request cho filter/pagination
 */
@Data
public class BaseFilterRequest {
    @Min(value = 0, message = "Page index must be greater than or equal to 0")
    @Schema(defaultValue = "0")
    private int page = 0;
    @Min(value = 1, message = "Entry size must be greater than 0")
    @Schema(defaultValue = "10")
    @NotBlank(message = "")
    private int entry = 10;

    @NotBlank(message = "Field to sort must not be blank")
    @Schema(defaultValue = "id")
    private String field = "id";

    @NotNull(message = "Sort direction must not be null")
    @Schema(defaultValue = "DESC", allowableValues = {"ASC", "DESC"})
    private Sort sort = Sort.DESC;
}
