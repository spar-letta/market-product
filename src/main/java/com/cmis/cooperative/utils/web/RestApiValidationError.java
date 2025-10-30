package com.cmis.cooperative.utils.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestApiValidationError {

    private String fieldName, message;
    public RestApiValidationError(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}