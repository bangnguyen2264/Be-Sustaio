package com.codejava.course.repository;

import com.codejava.course.model.entity.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<MediaFile, UUID> {
    boolean existsImageById(UUID id);
}
