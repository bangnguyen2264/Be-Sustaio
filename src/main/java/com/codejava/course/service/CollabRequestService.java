package com.codejava.course.service;

import com.codejava.course.model.constant.Status;
import com.codejava.course.model.dto.CollabRequestDto;
import com.codejava.course.model.form.CollabRequestForm;

import java.util.List;

public interface CollabRequestService {
    CollabRequestDto createCollabRequest(CollabRequestForm collabRequestForm);
    String deleteCollabRequest(Long id);
    List<CollabRequestDto> getMyCollabRequests();
    CollabRequestDto getCollabRequestById(long id);
    CollabRequestDto updateStatusCollabRequest(Status status, long id);
}
