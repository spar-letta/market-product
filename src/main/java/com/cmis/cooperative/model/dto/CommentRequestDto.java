package com.cmis.cooperative.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CommentRequestDto(@NotEmpty String comment, UUID commentParentPublicId) {
}
