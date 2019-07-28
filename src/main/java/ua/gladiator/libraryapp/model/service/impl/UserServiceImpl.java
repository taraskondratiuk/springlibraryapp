package ua.gladiator.libraryapp.model.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.entity.User;
import ua.gladiator.libraryapp.model.repository.RoleRepository;
import ua.gladiator.libraryapp.model.repository.UserRepository;
import ua.gladiator.libraryapp.model.service.UserService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        //fixme
    }



    @Override
    public User getCurrentUser() {
        //todo
        return userRepository.getOne(1L);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User registerUser(User user) {
        //todo throw exc if already exist
        user.setRoles(Collections.singletonList(roleRepository.findByName("READER")));
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
}
