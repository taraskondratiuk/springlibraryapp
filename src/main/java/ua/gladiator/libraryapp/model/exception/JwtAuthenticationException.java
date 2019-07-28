package ua.gladiator.libraryapp.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "jwt token is expired or invalid")
public class JwtAuthenticationException extends RuntimeException {
}
