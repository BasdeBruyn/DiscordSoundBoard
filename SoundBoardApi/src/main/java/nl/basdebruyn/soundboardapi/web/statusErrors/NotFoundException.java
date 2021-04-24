package nl.basdebruyn.soundboardapi.web.statusErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public NotFoundException() {
        super(httpStatus);
    }

    public NotFoundException(String reason, Object... params) {
        super(httpStatus, String.format(reason, params));
    }
}
