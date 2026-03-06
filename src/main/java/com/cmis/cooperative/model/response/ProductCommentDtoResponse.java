package com.cmis.cooperative.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ProductCommentDtoResponse(UUID publicId,
                                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                        LocalDate createdDate,
                                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
                                        Time timeCreated,
                                        String createdByName,
                                        String comment,
                                        String parentCommentText,
                                        String parentCommentOwnerName) {
}
