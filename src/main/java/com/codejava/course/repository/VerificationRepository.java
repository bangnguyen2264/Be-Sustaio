package com.codejava.course.repository;

import com.codejava.course.model.entity.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<VerificationRequest, Long> {
}
