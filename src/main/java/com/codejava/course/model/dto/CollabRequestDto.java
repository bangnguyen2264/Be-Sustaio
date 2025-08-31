package com.codejava.course.model.dto;

import com.codejava.course.model.entity.CollaborationRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollabRequestDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String description;
    private String photographicEvidenceUrl; //ảnh minh chứng
    private String status;
    private CollabDto collabDto;
    private UserDto userDto;

    public static CollabRequestDto from(CollaborationRequest collaborationRequest) {
        return CollabRequestDto.builder()
                .id(collaborationRequest.getId())
                .fullName(collaborationRequest.getFullName())
                .email(collaborationRequest.getEmail())
                .phone(collaborationRequest.getPhone())
                .address(collaborationRequest.getAddress())
                .description(collaborationRequest.getDescription())
                .photographicEvidenceUrl(collaborationRequest.getPhotographicEvidenceUrl())
                .status(collaborationRequest.getStatus().toString())
                .collabDto(CollabDto.from(collaborationRequest.getCollaboration()))
                .userDto(UserDto.from(collaborationRequest.getUser()))
                .build();
    }
}
