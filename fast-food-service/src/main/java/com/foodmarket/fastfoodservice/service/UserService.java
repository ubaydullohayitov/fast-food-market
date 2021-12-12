package com.foodmarket.fastfoodservice.service;

import com.foodmarket.fastfoodservice.domain.User;
import com.foodmarket.fastfoodservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Boolean checkUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }
    public void delete(Long id){
        User user = userRepository.getById(id);
        userRepository.delete(user);
    }

}
