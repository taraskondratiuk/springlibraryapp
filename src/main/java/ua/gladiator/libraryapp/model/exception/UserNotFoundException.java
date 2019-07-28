package ua.gladiator.libraryapp.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "no user with such email")
public class UserNotFoundException extends RuntimeException {

}