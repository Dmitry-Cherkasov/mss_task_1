package org.mss.resource.exception;

import lombok.Getter;

@Getter
public class InvalidFileFormatException extends RuntimeException {
    private final String contentType;

    public InvalidFileFormatException(String contentType) {
        super("Invalid file format: " + contentType + ". Only MP3 files are allowed");
        this.contentType = contentType;
    }

}