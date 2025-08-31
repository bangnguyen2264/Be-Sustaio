package com.codejava.course.model.dto;

import com.codejava.course.model.entity.Collaboration;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollabDto {
    private Long id;
    private String content;
    private String otherContent;
    private String descEnterprise;
    private String address;
    private Boolean verified;
    private String coverImageUrl;

    private CategoryDto categoryDto;
    private UserDto userDto;

    public static CollabDto from(Collaboration collaboration) {
        return CollabDto.builder()
                .id(collaboration.getId())
                .content(collaboration.getContent())
                .otherContent(collaboration.getOtherContent())
                .descEnterprise(collaboration.getDescEnterprise())
                .address(collaboration.getAddress())
                .verified(collaboration.getVerified())
                .coverImageUrl(collaboration.getCoverImageUrl())
                .categoryDto(CategoryDto.from(collaboration.getCategory()))
                .userDto(UserDto.from(collaboration.getUser()))
                .build();
    }
}
