package com.cmis.cooperative.utils.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum RestApiHttpStatus {

    /**
     * {@code 200 OK } Use when everything went well.
     */
    OK(200, "Ok"),

    /**
     * {@code 500 Internal Server Error} Use when there is a problem on the server that is not caused by something that the client did.
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    /**
     * {@code 400 Bad Request} Use when something is wrong in the input form the client, such as missing fields or invalid data.
     */
    BAD_REQUEST(400, "Bad Request"),

    /**
     * {@code 401 Forbidden} When the requester is not logged in, it means that the client should log in before sending the request again.
     */

    UNAUTHORIZED(401, "Unauthorized"),

    /**
     * {@code 403 Forbidden} When when the requester does not have permission to carry out an operation. The requester is logged in but they
     * don't have permission to perform the operation, re-sending the request will still cause a 403.
     */
    FORBIDDEN(403, "Forbidden"),

    /**
     * {@code 404 Forbidden} When the URL the request was received on was not found.
     */
    NOT_FOUND(404, "Not Found");


    private final int statusCode;
    private final String description;

    private RestApiHttpStatus(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    @JsonCreator
    public static RestApiHttpStatus parse(@JsonProperty("statusCode") int statusCode) {
        switch (statusCode) {
            case 200:
                return OK;
            case 500:
                return INTERNAL_SERVER_ERROR;
            case 400:
                return BAD_REQUEST;
            case 401:
                return UNAUTHORIZED;
            case 403:
                return FORBIDDEN;
            case 404:
                return NOT_FOUND;
            default:
                throw new IllegalArgumentException("Invalid RestApiStatus Code " + statusCode);
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return description;
    }

}