package org.pati.polish_to_utf_encoding.json_converter_logic;

import lombok.extern.slf4j.Slf4j;
import org.pati.polish_to_utf_encoding.json_converter_logic.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.charset.MalformedInputException;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("Failed to read request body: {}", ex.getMessage());

        String errorMessage = "Invalid request body. Ensure it is a valid JSON.";

        if (ex.getCause() instanceof MalformedInputException) {
            errorMessage = "Character encoding error. The payload must be in ISO-8859-2.";
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errorMessage);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An internal server error occurred.");
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
