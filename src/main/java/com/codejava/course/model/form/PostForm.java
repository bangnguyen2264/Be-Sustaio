package com.codejava.course.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Request form for creating a new Post.
 */
@Data
@Schema(description = "Request form for creating a new post using multipart/form-data")
public class PostForm {

    @NotBlank(message = "Title must not be blank")
    @Schema(description = "The title of the post", requiredMode = Schema.RequiredMode.REQUIRED, defaultValue = "My Post")
    private String title;

    @NotBlank(message = "Content must not be blank")
    @Schema(description = "The content of the post", requiredMode = Schema.RequiredMode.REQUIRED, defaultValue = "This is the content of the post")
    private String content;

    @NotNull(message = "Category ID must not be null")
    @Schema(description = "The ID of the category to which the post belongs", requiredMode = Schema.RequiredMode.REQUIRED, defaultValue = "1")
    private Long categoryId;
    @NotNull(message = "Image file must not be null")
    @Schema(description = "Image file for the post (e.g., PNG, JPEG).", requiredMode = Schema.RequiredMode.REQUIRED, type = "string", format = "binary", defaultValue = "")
    private MultipartFile imageFile;
}