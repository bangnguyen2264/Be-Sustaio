package com.codejava.course.controller;

import com.codejava.course.model.dto.ApiResponse;
import com.codejava.course.model.dto.PostDto;
import com.codejava.course.model.form.PostForm;
import com.codejava.course.model.form.PostUpdateForm;
import com.codejava.course.model.request.PostFilterRequest;
import com.codejava.course.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    // ------------------- GET ALL -------------------
    @GetMapping
    public ResponseEntity<ApiResponse<PostDto>> getAll(@Valid @ParameterObject PostFilterRequest request) {
        ApiResponse<PostDto> response = postService.getAll(request);
        return ResponseEntity.ok(response);
    }

    // ------------------- GET BY ID -------------------
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable Long id) {
        PostDto postDto = postService.getById(id);
        return ResponseEntity.ok(postDto);
    }

    // ------------------- CREATE -------------------
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Create a new post",
            description = "Creates a new post with title, content, category ID, and optional image file. Use multipart/form-data to upload the image file (PNG, JPEG) or omit it."
    )
    public ResponseEntity<PostDto> createPost(@Valid @Schema(implementation = PostForm.class) PostForm form) {
        PostDto postDto = postService.create(form);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    // ------------------- UPDATE -------------------
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Update an existing post",
            description = "Updates a post partially by its ID. Provide any of title, content, category ID, or image file. Use multipart/form-data for image."
    )
    public ResponseEntity<PostDto> updatePost(
            @PathVariable Long id,
            @Valid @Schema(implementation = PostUpdateForm.class) PostUpdateForm form
    ) {
        return ResponseEntity.ok(postService.update(id, form));
    }

    // ------------------- DELETE -------------------
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a post", description = "Deletes a post by its ID.")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok().build();
    }
}
