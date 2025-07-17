package org.pati.polish_to_utf_encoding.json_converter_logic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiError {

    private final HttpStatus status;

    private final String message;

}