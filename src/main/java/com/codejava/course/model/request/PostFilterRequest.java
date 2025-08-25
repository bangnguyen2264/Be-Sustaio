package com.codejava.course.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class PostFilterRequest extends BaseFilterRequest {
    private String title;
}
