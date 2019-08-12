package ua.gladiator.libraryapp.model.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "user with such email already exists")
public class EmailAlreadyExistsException extends RuntimeException{
}
