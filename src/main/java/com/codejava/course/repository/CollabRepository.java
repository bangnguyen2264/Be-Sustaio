package com.codejava.course.repository;

import com.codejava.course.model.entity.Collaboration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollabRepository extends JpaRepository<Collaboration, Long> {
    List<Collaboration> findAllByCategory_Id(long categoryId);
}
