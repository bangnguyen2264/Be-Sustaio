package com.codejava.course.service;

import com.codejava.course.model.entity.VerificationRequest;
import com.codejava.course.model.form.VerificationRequestForm;
import com.codejava.course.model.form.VerificationUpdateForm;
import com.codejava.course.model.request.VerificationFilterRequest;

public interface VerificationService extends BaseService<VerificationRequest, VerificationRequestForm, VerificationUpdateForm, VerificationFilterRequest> {
}
