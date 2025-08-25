package com.codejava.course.controller;

import com.codejava.course.model.dto.ApiResponse;
import com.codejava.course.model.dto.PostDto;
import com.codejava.course.model.request.PostFilterRequest;
import com.codejava.course.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;
    @GetMapping()
    public ResponseEntity<ApiResponse<PostDto>> filterPosts(
            @Valid @ParameterObject PostFilterRequest request
    ) {
        return ResponseEntity.ok(postService.getAll(request)
        );
    }
}
