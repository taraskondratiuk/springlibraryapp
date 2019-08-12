package ua.gladiator.libraryapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.gladiator.libraryapp.model.entity.User;
import ua.gladiator.libraryapp.model.exception.UserNotFoundException;
import ua.gladiator.libraryapp.model.service.UserService;
import ua.gladiator.libraryapp.security.JwtProvider;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AccountsRestController {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserService userService;

    @Resource
    private JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            return userService.getUserByEmail(user.getEmail())
                    .map(u -> {
                        String token = jwtProvider.createToken(u.getEmail(), u.getRoles());

                        Map<Object, Object> response = new HashMap<>();
                        response.put("username", u.getEmail());
                        response.put("token", token);
                        response.put("role", u.getRoles().get(0).getName());

                        return ResponseEntity.ok(response);
                    }).orElseThrow(UserNotFoundException::new);


        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }
}
