package com.codejava.course.repository;

import com.codejava.course.model.entity.Collaboration;
import com.codejava.course.model.entity.CollaborationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CollaborationRequestRepository extends JpaRepository<CollaborationRequest, Long>, JpaSpecificationExecutor<CollaborationRequest> {
    List<CollaborationRequest> findAllByUser_Username(String username);
}
