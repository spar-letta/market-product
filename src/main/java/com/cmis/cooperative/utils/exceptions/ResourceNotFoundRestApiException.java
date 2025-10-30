package com.cmis.cooperative.utils.exceptions;

import com.cmis.cooperative.utils.web.RestApiException;
import com.cmis.cooperative.utils.web.RestApiHttpStatus;

public class ResourceNotFoundRestApiException extends RestApiException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundRestApiException() {
        super(RestApiHttpStatus.NOT_FOUND);
    }
}