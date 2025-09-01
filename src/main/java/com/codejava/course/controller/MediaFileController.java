package com.codejava.course.controller;

import com.codejava.course.model.dto.MediaFileUrlDto;
import com.codejava.course.service.MediaFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MediaFileController {
    private final MediaFileService mediaFileService;

    @PostMapping(value = "/api/v1/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MediaFileUrlDto> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return mediaFileService.uploadImage(file);
    }

    @GetMapping(path = "/api/v1/image/get/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") UUID id) throws IOException {
        return mediaFileService.getImageById(id);
    }
}