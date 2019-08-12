package ua.gladiator.libraryapp.model.service;


import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.entity.User;

import java.util.Optional;

@Service
public interface UserService {
    User getUserByToken(String token);

    Optional<User> getUserByEmail(String email);

    User registerUser(User user);
}
