package com.cmis.cooperative.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ProductDtoResponse(UUID publicId,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                 LocalDate dateCreated,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
                                 Time timeCreated,
                                 String ownerFirstName,
                                 String itemName,
                                 String description,
                                 BigDecimal price) {
}
