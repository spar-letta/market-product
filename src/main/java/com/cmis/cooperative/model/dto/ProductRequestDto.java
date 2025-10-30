package com.cmis.cooperative.model.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductRequestDto(String name,
                                String description,
                                BigDecimal price) {
}
