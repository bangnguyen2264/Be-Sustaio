package com.codejava.course.service.impl;

import com.codejava.course.exception.NotFoundException;
import com.codejava.course.model.dto.ApiResponse;
import com.codejava.course.model.dto.CollabDto;
import com.codejava.course.model.entity.Collaboration;
import com.codejava.course.model.request.CollaborationFilterRequest;
import com.codejava.course.repository.CategoryRepository;
import com.codejava.course.repository.CollaborationRepository;
import com.codejava.course.repository.UserRepository;
import com.codejava.course.service.CollabService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollabServiceImpl implements CollabService {
    private final CollaborationRepository collaborationRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<CollabDto> getAllCollabs(CollaborationFilterRequest collaborationFilterRequest) {
        Sort sort = Sort.by(
                collaborationFilterRequest.getSort() == com.codejava.course.model.constant.Sort.DESC
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                collaborationFilterRequest.getField() != null ? collaborationFilterRequest.getField() : "id"
        );
        Pageable pageable = PageRequest.of(collaborationFilterRequest.getPage(), collaborationFilterRequest.getEntry(), sort);
        Specification<Collaboration> spec = Specification
                .where(hasUserId(collaborationFilterRequest.getUserId()))
                .and(hasCategoryId(collaborationFilterRequest.getCategoryId()));
        Page<Collaboration> page = collaborationRepository.findAll(spec, pageable);
        Page<CollabDto> dtoPage = page.map(CollabDto::from);
        return ApiResponse.fromPage(dtoPage);
    }

    @Override
    public CollabDto getCollabById(long id) {
        return CollabDto.from(collaborationRepository.findById(id)
                .orElseThrow(()
                        -> new IllegalArgumentException("Collab not found with id: " + id)));
    }

    private Specification<Collaboration> hasUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found with id: " + userId);
        }
        return (root, query, cb) -> userId == null ? null : cb.equal(root.get("user").get("id"), userId);
    }

    private Specification<Collaboration> hasCategoryId(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Category not found with id: " + categoryId);
        }
        return (root, query, cb) -> categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }
}
