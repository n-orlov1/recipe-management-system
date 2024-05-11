package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.example.entity.User;
import org.example.service.UserService;

import java.util.Optional;
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/register")
    public ResponseEntity registerNewUser(@RequestBody User user) {

        if(!user.validate()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> registeredUser = userService.registerNewUser(user);

        if(registeredUser.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
