package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.entity.User;
import org.example.repository.UserRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<User> registerNewUser(User user) {

        if(userRepository.existsByEmail(user.getEmail())) {
            return Optional.empty();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return Optional.of(userRepository.save(user));
    }
}
