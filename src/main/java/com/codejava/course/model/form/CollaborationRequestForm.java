package com.codejava.course.model.form;

import com.codejava.course.model.constant.Status;
import com.codejava.course.model.entity.CollaborationRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CollaborationRequestForm {
    @NotEmpty(message = "Name is required")
    @Min(value = 5, message = "Username must be at least 5 characters long")
    private String fullName;
    @NotEmpty(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;
    @NotEmpty(message = "Phone is required")
    @Min(value = 10, message = "Phone must be at least 10 characters long")
    private String phone;
    @NotEmpty(message = "Address is required")
    @Min(value = 5, message = "Address must be at least 5 characters long")
    private String address;
    @NotEmpty(message = "Description is required")
    private String description;
    private String photographicEvidenceUrl; //ảnh minh chứng
    @NotNull(message = "Collab id is required")
    private long collabId;

    public static CollaborationRequest toEntity(CollaborationRequestForm collaborationRequestForm) {
        return CollaborationRequest.builder()
                .fullName(collaborationRequestForm.getFullName())
                .email(collaborationRequestForm.getEmail())
                .phone(collaborationRequestForm.getPhone())
                .address(collaborationRequestForm.getAddress())
                .description(collaborationRequestForm.getDescription())
                .photographicEvidenceUrl(collaborationRequestForm.getPhotographicEvidenceUrl())
                .status(Status.PENDING)
                .build();
    }
}
