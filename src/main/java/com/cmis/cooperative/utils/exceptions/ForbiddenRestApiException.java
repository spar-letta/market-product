package com.cmis.cooperative.utils.exceptions;

import com.cmis.cooperative.utils.web.RestApiException;
import com.cmis.cooperative.utils.web.RestApiHttpStatus;

public class ForbiddenRestApiException extends RestApiException {
    private static final long serialVersionUID = 1L;

    public ForbiddenRestApiException() {
        super(RestApiHttpStatus.FORBIDDEN);
    }
}
