package com.cmis.cooperative.utils.exceptions;

import com.cmis.cooperative.utils.web.RestApiException;
import com.cmis.cooperative.utils.web.RestApiHttpStatus;

public class ConstraintValidationException extends RestApiException {
    private static final long serialVersionUID = 1L;

    public ConstraintValidationException() {
        super(RestApiHttpStatus.INTERNAL_SERVER_ERROR);
    }
}