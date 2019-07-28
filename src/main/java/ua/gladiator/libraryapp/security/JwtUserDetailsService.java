package ua.gladiator.libraryapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.exception.UserNotFoundException;
import ua.gladiator.libraryapp.model.service.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        return userService.getUserByEmail(email)
                .map(JwtUserFactory::create)
                .orElseThrow(UserNotFoundException::new);
    }
}