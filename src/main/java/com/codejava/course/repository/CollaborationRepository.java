package com.codejava.course.repository;

import com.codejava.course.model.entity.Collaboration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CollaborationRepository extends JpaRepository<Collaboration, Long>, JpaSpecificationExecutor<Collaboration> {
    List<Collaboration> findAllByCategory_Id(long categoryId);
}
