package org.mss.song.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ErrorResponse {
    // Getters & setters
    private String errorMessage;
    private Map<String, String> details;
    private String errorCode;

    // Constructor
    public ErrorResponse(String errorMessage, Map<String, String> details, String errorCode) {
        this.errorMessage = errorMessage;
        this.details = details;
        this.errorCode = errorCode;
    }

}