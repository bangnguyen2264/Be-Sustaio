package com.codejava.course.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class ApiResponse<D> {
    private List<D> data;     // danh sách dữ liệu
    private int page;         // số trang hiện tại
    private long total;       // tổng số item trong DB

    // factory method để chuyển từ Page sang ApiResponse
    public static <D> ApiResponse<D> fromPage(Page<D> page) {
        return ApiResponse.<D>builder()
                .data(page.getContent())
                .page(page.getNumber())
                .total(page.getTotalElements())
                .build();
    }
}
