package com.codejava.course.controller;

import com.codejava.course.model.dto.ImageUrlDto;
import com.codejava.course.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping(value = "/api/v1/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageUrlDto> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return imageService.uploadImage(file);
    }

    @GetMapping(path = "/api/v1/image/get/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") UUID id) throws IOException {
        return imageService.getImageById(id);
    }
}