package com.codejava.course.controller;

import com.codejava.course.model.dto.ApiResponse;
import com.codejava.course.model.entity.VerificationRequest;
import com.codejava.course.model.form.VerificationRequestForm;
import com.codejava.course.model.form.VerificationUpdateForm;
import com.codejava.course.model.request.VerificationFilterRequest;
import com.codejava.course.service.VerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/verifications")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    // Lấy danh sách có phân trang, filter, sort
    @GetMapping
    public ResponseEntity<ApiResponse<VerificationRequest>> getAll(@Valid @ParameterObject VerificationFilterRequest filterRequest) {
        return ResponseEntity.ok(verificationService.getAll(filterRequest));
    }

    // Lấy theo id
    @GetMapping("/{id}")
    public ResponseEntity<VerificationRequest> getById(@PathVariable Long id) {
        return ResponseEntity.ok(verificationService.getById(id));
    }

    // Tạo mới
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VerificationRequest> create( @Valid @ModelAttribute VerificationRequestForm form) {
        return ResponseEntity.ok(verificationService.create(form));
    }

    // Cập nhật
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VerificationRequest> update(
            @PathVariable Long id,
            @RequestBody VerificationUpdateForm form
    ) {
        return ResponseEntity.ok(verificationService.update(id, form));
    }

    // Xóa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        verificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
