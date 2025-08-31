package com.codejava.course.model.entity;

import com.codejava.course.model.constant.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationRequest extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // User gửi yêu cầu xác minh
    private User user;
    @Column(nullable = false)
    private String name;
    // Tên hộ nông nghiệp hoặc doanh nghiệp

    private String taxCode;
    // Mã số thuế (nếu là doanh nghiệp)

    private String businessLicenseNumber;
    // Số giấy phép kinh doanh
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private MediaFile certificateDocument;
    // Giấy chứng nhận hộ nông nghiệp hoặc doanh nghiệp

    private String address;
    // Địa chỉ hộ/đơn vị

    private String contactPerson;
    // Người đại diện

    private String phoneNumber;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    // PENDING, APPROVED, REJECTED

    private String rejectionReason;

}
