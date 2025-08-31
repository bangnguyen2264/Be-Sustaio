package com.codejava.course.model.form;

import com.codejava.course.model.constant.Status;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VerificationUpdateForm {
    @NotEmpty
    private Status status;
    private String rejectionReason;
}
