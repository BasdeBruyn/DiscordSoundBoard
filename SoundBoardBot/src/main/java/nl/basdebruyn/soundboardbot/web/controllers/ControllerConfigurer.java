package nl.basdebruyn.soundboardbot.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ControllerConfigurer {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> createResponseFromException(ResponseStatusException exception) {
        return new ResponseEntity<>(exception.getReason(), exception.getStatus());
    }
}
