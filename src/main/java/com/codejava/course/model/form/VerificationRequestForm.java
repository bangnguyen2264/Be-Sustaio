package com.codejava.course.model.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;

@Data
public class VerificationRequestForm {

    @NotNull(message = "User ID is required")
    private Long userId;   // ID of the user
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Tax code cannot be empty")
    @Size(max = 50, message = "Tax code must not exceed 50 characters")
    private String taxCode;   // Tax code

    @NotBlank(message = "Business license number is required")
    @Size(max = 100, message = "Business license number must not exceed 100 characters")
    private String businessLicenseNumber;  // Business license

    @NotNull(message = "Certificate document is required")
    private String certificateDocument; // Certificate document

    @NotBlank(message = "Address cannot be empty")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;   // Address

    @NotBlank(message = "Contact person name is required")
    @Size(max = 100, message = "Contact person name must not exceed 100 characters")
    private String contactPerson;   // Contact person

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{9,15}$", message = "Phone number must contain 9 to 15 digits")
    private String phoneNumber;   // Phone number

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;   // Email
}
