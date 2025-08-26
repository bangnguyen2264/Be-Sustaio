package com.codejava.course.service;

import com.codejava.course.model.dto.ImageUrlDto;
import com.codejava.course.model.entity.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ImageService {
    ResponseEntity<ImageUrlDto> uploadImage(MultipartFile file) throws IOException;
    ResponseEntity<byte[]> getImageById(UUID id) throws IOException;
    Image saveImage(MultipartFile file) throws IOException;

}