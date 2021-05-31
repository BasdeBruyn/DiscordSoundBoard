package nl.basdebruyn.soundboardapi.web.configurers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ControllerConfigurer {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> createResponseFromException(ResponseStatusException exception) {
        return new ResponseEntity<>(exception.getReason(), exception.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> createResponseFromException(MethodArgumentNotValidException exception) {
        String message = createValidationErrorMessage(exception);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    private String createValidationErrorMessage(MethodArgumentNotValidException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Values of ").append(getParameterName(exception)).append(" are invalid");

        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            appendFieldErrorMessage(stringBuilder, error);
        }

        return stringBuilder.toString();
    }

    private String getParameterName(MethodArgumentNotValidException exception) {
        return exception.getParameter().getParameterName();
    }

    private void appendFieldErrorMessage(StringBuilder stringBuilder, FieldError error) {
        stringBuilder
                .append('\n')
                .append(error.getField()).append(": ")
                .append(error.getDefaultMessage());
    }
}
