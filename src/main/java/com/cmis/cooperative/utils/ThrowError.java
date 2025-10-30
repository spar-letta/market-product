package com.cmis.cooperative.utils;

import com.cmis.cooperative.utils.exceptions.BadRequestRestApiException;
import org.springframework.stereotype.Component;

@Component
public class ThrowError {
    public void throwException(String errorMessage){
        throw new BadRequestRestApiException()
                .developerMessage(errorMessage)
                .userMessage("Sorry, %s", errorMessage);
    }
}
