package org.mss.resource.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFileFormat(InvalidFileFormatException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("errorMessage", ex.getMessage());
        response.put("errorCode", "400");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotValidIdLength.class)
    public ResponseEntity<Map<String, Object>> handleInvalidIdsLength(NotValidIdLength ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "errorMessage", String.format("CSV string is too long: received %s characters, maximum allowed is 200", ex.getMessage()),
                        "errorCode", "400"
                ));
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidId(InvalidIdException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "errorMessage", String.format("Invalid value '%s' for ID. Must be a positive integer", ex.getMessage()),
                        "errorCode", "400"
                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                "errorMessage", String.format("Invalid value '%s' for ID. Must be a positive integer", ex.getMessage()),
                "errorCode", "400"
                ));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(FileNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "errorMessage", String.format("Resource with ID=%s not found", ex.getMessage()),
                "errorCode", "404"
        ));
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(DatabaseException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "errorMessage", "An error occurred on the server",
                "errorCode", "500"
        ));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "Invalid file format: " + ex.getContentType() + ". Only MP3 files are allowed",
                        "errorCode", "400"
                ));
    }
}