package com.codejava.course.service.impl;

import com.codejava.course.exception.BadRequestException;
import com.codejava.course.model.dto.ImageUrlDto;
import com.codejava.course.model.entity.Image;
import com.codejava.course.repository.ImageRepository;
import com.codejava.course.service.ImageService;
import com.codejava.course.utils.AppUtils;
import com.codejava.course.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final AppUtils appUtils;

    @Override
    public ResponseEntity<ImageUrlDto> uploadImage(MultipartFile file) throws IOException {
        if (isImage(file)) {
            Image image = imageRepository.save(Image.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .data(ImageUtils.compressImage(file.getBytes())).build());

            String imageUrl = appUtils.getBaseUrlApi() + "/api/v1/image/get/" + image.getId();
            return new ResponseEntity<>(ImageUrlDto.builder()
                    .imageUrl(imageUrl)
                    .build(), HttpStatus.OK);
        } else {
            throw new BadRequestException("Invalid file");
        }
    }

    @Override
    public ResponseEntity<byte[]> getImageById(UUID id) throws IOException {
        Optional<Image> dbImage = imageRepository.findById(id);
        if (dbImage.isPresent()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(dbImage.get().getType()))
                    .body(ImageUtils.decompressImage(dbImage.get().getData()));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public Image saveImage(MultipartFile file) throws IOException {

        if (isImage(file)) {
            Image image = imageRepository.save(Image.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .data(ImageUtils.compressImage(file.getBytes())).build());
            return imageRepository.save(image);
        } else {
            throw new IOException("File is not an image");
        }
    }

    private boolean isImage(MultipartFile file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            return bufferedImage != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
