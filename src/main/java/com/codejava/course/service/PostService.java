package com.codejava.course.service;

import com.codejava.course.model.dto.PostDto;
import com.codejava.course.model.entity.Post;
import com.codejava.course.model.form.PostForm;
import com.codejava.course.model.form.PostUpdateForm;
import com.codejava.course.model.request.PostFilterRequest;

import java.util.List;

public interface PostService extends BaseService<PostDto, PostForm, PostUpdateForm, PostFilterRequest> {

}
