package org.example.authservice.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.AuthRequest;
import org.example.authservice.dto.AuthResponse;
import org.example.authservice.dto.RegisterRequest;
import org.example.authservice.entity.UserCredential;
import org.example.authservice.exception.UserWithEmailExistException;
import org.example.authservice.exception.UserWithUsernameExistException;
import org.example.authservice.mapper.UserMapper;
import org.example.authservice.repository.UserCredentialRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper mapper;

    @Transactional
    public void saveUser(RegisterRequest registerRequest) {
        if (repository.existsByUsername(registerRequest.getUsername())) {
            throw new UserWithUsernameExistException("Пользователь с именем  = " + registerRequest.getUsername() + " уже существует");
        }
        if (repository.existsByEmail(registerRequest.getEmail())) {
            throw new UserWithEmailExistException("Пользователь с email = " + registerRequest.getEmail() + " уже существует");
        }
        UserCredential userCredential = mapper.toUserEntity(registerRequest);
        userCredential.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        repository.save(userCredential);
    }

    public AuthResponse login(@NonNull AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        String token = generateToken(authRequest.getUsername());
        return new AuthResponse(token);
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
