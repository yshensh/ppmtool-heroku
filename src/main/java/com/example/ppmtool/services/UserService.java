package com.example.ppmtool.services;

import com.example.ppmtool.domain.User;
import com.example.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.example.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser) {

        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            // username has to be unique
            newUser.setUsername(newUser.getUsername());

            // don't show un-encoded confirmPassword
            newUser.setConfirmPassword("");

            // password and confirmPassword match
            return userRepository.save(newUser);

        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
        }

    }
}
