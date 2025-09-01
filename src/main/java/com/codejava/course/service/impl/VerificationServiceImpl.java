package com.codejava.course.service.impl;

import com.codejava.course.exception.NotFoundException;
import com.codejava.course.model.constant.Status;
import com.codejava.course.model.dto.ApiResponse;
import com.codejava.course.model.entity.MediaFile;
import com.codejava.course.model.entity.User;
import com.codejava.course.model.entity.VerificationRequest;
import com.codejava.course.model.form.VerificationRequestForm;
import com.codejava.course.model.form.VerificationUpdateForm;
import com.codejava.course.model.request.VerificationFilterRequest;
import com.codejava.course.repository.UserRepository;
import com.codejava.course.repository.VerificationRepository;
import com.codejava.course.service.ImageService;
import com.codejava.course.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Override
    public ApiResponse<VerificationRequest> getAll(VerificationFilterRequest filterRequest) {
        //Tạo sort
        Sort sort = Sort.by(
                filterRequest.getSort().equals(com.codejava.course.model.constant.Sort.DESC)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC,
                filterRequest.getField() != null ? filterRequest.getField() : "id"
        );// Tạo pageable
        Pageable pageable = PageRequest.of(filterRequest.getPage(), filterRequest.getEntry(), sort);

        Page<VerificationRequest> page = verificationRepository.findAll(pageable);
        return ApiResponse.fromPage(page);
    }

    @Override
    public VerificationRequest getById(Long id) {
        return verificationRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Verification request not found with id: " + id)
        );
    }

    @Override
    public VerificationRequest create(VerificationRequestForm createRequest) {
        User user = userRepository.findById(createRequest.getUserId()).orElseThrow(
                ()-> new NotFoundException("User not found")
        );
        VerificationRequest verificationRequest = VerificationRequest.builder()
                .user(user)
                .name(createRequest.getName())
                .email(createRequest.getEmail())
                .address(createRequest.getAddress())
                .phoneNumber(createRequest.getPhoneNumber())
                .businessLicenseNumber(createRequest.getBusinessLicenseNumber())
                .taxCode(createRequest.getTaxCode())
                .contactPerson(createRequest.getContactPerson())
                .status(Status.PENDING)
                .build();
        handleCertificateDocument(createRequest.getCertificateDocument(), verificationRequest);

        return verificationRepository.save(verificationRequest);
    }

    @Override
    public VerificationRequest update(Long id, VerificationUpdateForm updateRequest) {
        VerificationRequest existingRequest = verificationRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Verification request not found with id: " + id)
        );

        existingRequest.setStatus(updateRequest.getStatus());
        if(updateRequest.getStatus() == Status.PENDING) {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("User not found with id: " + id)
            );
            user.setVerified(true);
            userRepository.save(user);
        }
        if (updateRequest.getStatus() == Status.REJECTED) {
            if (updateRequest.getRejectionReason() == null || updateRequest.getRejectionReason().isBlank()) {
                throw new NotFoundException("Rejection reason is required when status is REJECTED");
            }
            existingRequest.setRejectionReason(updateRequest.getRejectionReason());
        } else {
            existingRequest.setRejectionReason(null);
        }

        return verificationRepository.save(existingRequest);
    }


    @Override
    public void delete(Long id) {
        VerificationRequest existingRequest = verificationRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Verification request not found with id: " + id)
        );
        verificationRepository.delete(existingRequest);
    }

    private void handleCertificateDocument(MultipartFile imageFile, VerificationRequest verificationRequest) {
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                MediaFile savedImage = imageService.saveImage(imageFile);
                verificationRequest.setCertificateDocument(savedImage);
            } catch (Exception e) {
                throw new NotFoundException("Failed to save image: " + e.getMessage());
            }
        }
    }
}
