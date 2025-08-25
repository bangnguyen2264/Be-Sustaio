package com.codejava.course.service.impl;

import com.codejava.course.exception.BadRequestException;
import com.codejava.course.model.dto.ApiResponse;
import com.codejava.course.model.dto.PostDto;
import com.codejava.course.model.entity.Post;
import com.codejava.course.model.request.PostFilterRequest;
import com.codejava.course.repository.CategoryRepository;
import com.codejava.course.repository.PostRepository;
import com.codejava.course.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.codejava.course.model.dto.ApiResponse.fromPage;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;


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
        Pageable pageable = PageRequest.of(
                filterRequest.getPage(),
                filterRequest.getEntry(),
                sort
        );

        // Lấy dữ liệu từ DB
        Page<Post> postPage = postRepository.findAll(pageable);

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
        Post post = postRepository.findById(id).orElseThrow(
                () -> new BadRequestException("Post not found")
        );

        return PostDto.from(post);
    }

    @Override
    public PostDto create(PostDto dto) {
        return null;
    }

    @Override
    public PostDto update(Long id, PostDto dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
