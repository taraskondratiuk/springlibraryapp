package ua.gladiator.libraryapp.model.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.entity.User;
import ua.gladiator.libraryapp.model.exception.EmailAlreadyExistsException;
import ua.gladiator.libraryapp.model.exception.UserNotFoundException;
import ua.gladiator.libraryapp.model.repository.RoleRepository;
import ua.gladiator.libraryapp.model.repository.UserRepository;
import ua.gladiator.libraryapp.model.service.UserService;
import ua.gladiator.libraryapp.security.JwtProvider;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private JwtProvider jwtProvider;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Override
    public User getUserByToken(String token) {
        String email = jwtProvider.getEmail(token.replace("Bearer_", ""));
        return userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User registerUser(User user) {
        user.setRoles(Collections.singletonList(roleRepository.findByName("READER")));
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        log.info("new user {} registered", user);
        return userRepository.save(user);
    }
}
