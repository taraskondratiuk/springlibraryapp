package ua.gladiator.libraryapp.model.service;


import ua.gladiator.libraryapp.model.entity.User;

import java.util.Optional;

public interface UserService {
    User getCurrentUser();

    Optional<User> getUserByEmail(String email);

    User registerUser(User user);
}
