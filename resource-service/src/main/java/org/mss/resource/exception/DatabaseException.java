package org.mss.resource.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super("An error occurred on the server");
    }
}
