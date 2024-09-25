package org.example.authservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.AuthRequest;
import org.example.authservice.dto.AuthResponse;
import org.example.authservice.entity.UserCredential;
import org.example.authservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void addNewUser(@RequestBody UserCredential user) {
        authService.saveUser(user);
    }

    //TODO: Бросаю общее исключение?
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> getToken(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/validate")
    public void validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
    }

}


