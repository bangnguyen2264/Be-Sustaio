package com.codejava.course.service;

import com.codejava.course.model.dto.ApiResponse;
import com.codejava.course.model.dto.CollabDto;
import com.codejava.course.model.request.CollaborationFilterRequest;

public interface CollabService {
    ApiResponse<CollabDto> getAllCollabs(CollaborationFilterRequest collaborationFilterRequest);
    CollabDto getCollabById(long id);
}
