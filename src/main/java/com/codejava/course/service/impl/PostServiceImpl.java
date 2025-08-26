package com.codejava.course.service.impl;

import com.codejava.course.exception.BadRequestException;
import com.codejava.course.model.dto.ApiResponse;
import com.codejava.course.model.dto.PostDto;
import com.codejava.course.model.entity.Category;
import com.codejava.course.model.entity.Image;
import com.codejava.course.model.entity.Post;
import com.codejava.course.model.form.PostForm;
import com.codejava.course.model.form.PostUpdateForm;
import com.codejava.course.model.request.PostFilterRequest;
import com.codejava.course.repository.CategoryRepository;
import com.codejava.course.repository.PostRepository;
import com.codejava.course.service.ImageService;
import com.codejava.course.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    @Override
    public ApiResponse<PostDto> getAll(PostFilterRequest filterRequest) {
        // Tạo sort
        Sort sort = Sort.by(
                filterRequest.getSort().equals(com.codejava.course.model.constant.Sort.DESC)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                filterRequest.getField() != null ? filterRequest.getField() : "id"
        );

        // Tạo pageable
        Pageable pageable = PageRequest.of(filterRequest.getPage(), filterRequest.getEntry(), sort);
        Specification<Post> specification = Specification.where(hasTitle(filterRequest.getTitle()));

        // Lấy dữ liệu từ DB
        Page<Post> postPage = postRepository.findAll(specification, pageable);

        // Convert sang PostDto
        Page<PostDto> postDtoPage = postPage.map(PostDto::from);

        // Trả về ApiResponse
        return ApiResponse.fromPage(postDtoPage);
    }

    @Override
    public PostDto getById(Long id) {
        if (id == null) {
            throw new BadRequestException("Id can't be null");
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Post not found"));
        return PostDto.from(post);
    }

    @Override
    public PostDto create(PostForm form) {
        // Validate và lấy category
        Category category = categoryRepository.findById(form.getCategoryId())
                .orElseThrow(() -> new BadRequestException("Category not found"));

        // Build Post entity
        Post post = Post.builder()
                .title(form.getTitle())
                .content(form.getContent())
                .category(category)
                .viewCount(0L)
                .build();

        handleImage(form.getImageFile(), post);

        Post savedPost = postRepository.save(post);
        return PostDto.from(savedPost);
    }

    @Override
    public PostDto update(Long id, PostUpdateForm dto) {
        if (id == null) {
            throw new BadRequestException("Id can't be null");
        }

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Post not found"));

        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            post.setTitle(dto.getTitle());
        }

        if (dto.getContent() != null && !dto.getContent().isBlank()) {
            post.setContent(dto.getContent());
        }

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new BadRequestException("Category not found"));
            post.setCategory(category);
        }

        handleImage(dto.getImageFile(), post);

        Post updatedPost = postRepository.save(post);
        return PostDto.from(updatedPost);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new BadRequestException("Id can't be null");
        }

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Post not found"));

        postRepository.delete(post);
    }

    // ------------------- PRIVATE HELPERS -------------------
    private Specification<Post> hasTitle(String title) {
        return (root, query, cb) -> title == null || title.isBlank()
                ? null
                : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    private void handleImage(MultipartFile imageFile, Post post) {
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Image savedImage = imageService.saveImage(imageFile);
                post.setImage(savedImage);
            } catch (Exception e) {
                throw new BadRequestException("Failed to save image: " + e.getMessage());
            }
        }
    }
}
