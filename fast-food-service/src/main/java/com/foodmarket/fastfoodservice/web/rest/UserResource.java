package com.foodmarket.fastfoodservice.web.rest;


import com.foodmarket.fastfoodservice.domain.User;
import com.foodmarket.fastfoodservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody User user) {
        if (!checkPasswordLength(user.getPassword())) {
            return new ResponseEntity<>("Oltidan ko'p raqam kiriting", HttpStatus.BAD_REQUEST);
        }
        if (userService.checkUserName(user.getUserName())) {
            return new ResponseEntity<>("Bu yuzer oldin ro'yxatdan o'tgan", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userService.create(user));
    }

    private Boolean checkPasswordLength(String password) {
        return password.length() >= 6;
    }
    @DeleteMapping("/register/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok("Kiritilgan yuzer o'chirildi");
    }


}
