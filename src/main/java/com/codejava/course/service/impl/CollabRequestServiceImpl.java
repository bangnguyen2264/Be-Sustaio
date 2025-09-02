package com.codejava.course.service.impl;

import com.codejava.course.model.constant.Status;
import com.codejava.course.model.dto.CollabRequestDto;
import com.codejava.course.model.entity.Collaboration;
import com.codejava.course.model.entity.CollaborationRequest;
import com.codejava.course.model.entity.User;
import com.codejava.course.model.form.CollaborationRequestForm;
import com.codejava.course.repository.CollaborationRepository;
import com.codejava.course.repository.CollaborationRequestRepository;
import com.codejava.course.repository.UserRepository;
import com.codejava.course.service.CollabRequestService;
import com.codejava.course.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CollabRequestServiceImpl implements CollabRequestService {
    private final CollaborationRequestRepository collaborationRequestRepository;
    private final UserRepository userRepository;
    private final CollaborationRepository collaborationRepository;

    @Override
    public CollabRequestDto createCollabRequest(CollaborationRequestForm collaborationRequestForm) {
        Collaboration collaboration = collaborationRepository.findById(collaborationRequestForm.getCollabId())
                .orElseThrow(() -> new IllegalArgumentException("Collab not found with id: " + collaborationRequestForm.getCollabId()));
        User user = userRepository.findByUsername(SecurityUtils.getUsernameOfPrincipal())
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + SecurityUtils.getUsernameOfPrincipal()));

        CollaborationRequest collaborationRequest = CollaborationRequestForm.toEntity(collaborationRequestForm);
        collaborationRequest.setCollaboration(collaboration);
        collaborationRequest.setUser(user);

        CollaborationRequest collaborationRequestCreated = collaborationRequestRepository.save(collaborationRequest);
        log.info("Collab Request with id {} created successfully", collaborationRequestCreated.getId());
        return CollabRequestDto.from(collaborationRequestCreated);
    }

    @Override
    public String deleteCollabRequest(Long id) {
        //kiểm tra quyền xóa
        if(!SecurityUtils.getUsernameOfPrincipal().equals(collaborationRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Collab Request not found with id: " + id))
                .getUser().getUsername())){
            throw new IllegalArgumentException("You don't have permission to delete this collab request");
        }
        collaborationRequestRepository.deleteById(id);

        log.info("Collab Request with id {} deleted successfully", id);
        return "Collab Request deleted successfully";
    }

    @Override
    public List<CollabRequestDto> getMyCollabRequests() {
        return collaborationRequestRepository.findAllByUser_Username(SecurityUtils.getUsernameOfPrincipal())
                .stream()
                .map(CollabRequestDto::from)
                .toList();
    }

    @Override
    public CollabRequestDto getCollabRequestById(long id) {
        return CollabRequestDto.from(collaborationRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Collab Request not found with id: " + id)));
    }

    @Override
    public CollabRequestDto updateStatusCollabRequest(Status status, long id) {
        //kiểm tra quyền sửa
        if(!SecurityUtils.getUsernameOfPrincipal().equals(collaborationRequestRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Collab Request not found with id: " + id))
                .getCollaboration().getUser().getUsername())){
            throw new IllegalArgumentException("You don't have permission to update this collab request");
        }

        //kiểm tra trạng thái đúng k
        if(status.equals("REJECTED") || status.equals("ACCEPTED")){
            CollaborationRequest collaborationRequest = collaborationRequestRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Collab Request not found with id: " + id));
            collaborationRequest.setStatus(status);

            log.info("Collab Request with id {} updated status to {} successfully", id, status);
            return CollabRequestDto.from(collaborationRequestRepository.save(collaborationRequest));
        }
        else {
            throw new IllegalArgumentException("Status is not valid");
        }
    }
}
