package com.foodmarket.fastfoodservice.web.rest;

import com.foodmarket.fastfoodservice.domain.User;
import com.foodmarket.fastfoodservice.repository.UserRepository;
import com.foodmarket.fastfoodservice.security.JwtTokenProvider;
import com.foodmarket.fastfoodservice.web.rest.vm.LoginVM;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserJwtController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;


    public UserJwtController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginVM loginVM) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword()));
        User user = userRepository.findByLogin(loginVM.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("Bu foydalanuvchi mavjud emas");
        }
        String token = jwtTokenProvider.createToken(user.getUserName(), user.getRoles());
        Map<Object, Object> map = new HashMap<>();
        map.put("username", user.getUserName());
        map.put("token", token);
        return ResponseEntity.ok(map);
    }
}
