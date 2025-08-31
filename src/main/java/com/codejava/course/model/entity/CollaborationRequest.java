package com.codejava.course.model.entity;

import com.codejava.course.model.constant.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "collab_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollaborationRequest extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    @Column(length = 1000000)
    private String description;
    private String photographicEvidenceUrl; //ảnh minh chứng
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.EAGER)
    private Collaboration collaboration;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
