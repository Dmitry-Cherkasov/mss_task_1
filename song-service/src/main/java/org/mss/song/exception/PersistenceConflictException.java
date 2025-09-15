package org.mss.song.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PersistenceConflictException extends RuntimeException {
    public PersistenceConflictException(String message) {
        super(message);
    }
}
