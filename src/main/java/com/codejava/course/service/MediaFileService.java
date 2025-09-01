package com.codejava.course.service;

import com.codejava.course.model.dto.MediaFileUrlDto;
import com.codejava.course.model.entity.MediaFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface MediaFileService {
    ResponseEntity<MediaFileUrlDto> uploadImage(MultipartFile file) throws IOException;
    ResponseEntity<byte[]> getImageById(UUID id) throws IOException;

}