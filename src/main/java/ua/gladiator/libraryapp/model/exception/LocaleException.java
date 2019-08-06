package ua.gladiator.libraryapp.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "cannot find locale file with such name")
public class LocaleException extends RuntimeException {
}
