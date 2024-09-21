package org.example.authservice.service;

import lombok.RequiredArgsConstructor;
import org.example.authservice.config.JwtAuthenticationFilter;
import org.example.authservice.dto.JwtAuthResponse;
import org.example.authservice.dto.Role;
import org.example.authservice.dto.SignInRequest;
import org.example.authservice.dto.SignUpRequest;
import org.example.authservice.entity.User;
import org.example.authservice.exception.UserWithUsernameExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthResponse signUp(SignUpRequest request) throws UserWithUsernameExistException {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        userService.create(user); // из другого сервиса вызов
        String jwt = jwtService.generateToken(user);
        return new JwtAuthResponse(jwt);
    }

    public JwtAuthResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        String jwt = jwtService.generateToken(user);
        return new JwtAuthResponse(jwt);
    }

    public ResponseEntity<Void> validateToken(String token) {
        // Убедитесь, что токен начинается с "Bearer "
        if (token == null || !token.startsWith(JwtAuthenticationFilter.BEARER_PREFIX)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String jwt = token.substring(JwtAuthenticationFilter.BEARER_PREFIX.length());

        // Проверка валидности токена
        String username = jwtService.extractUserName(jwt);
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

        if (userDetails != null && jwtService.isTokenValid(jwt, userDetails)) {
            return ResponseEntity.ok().build(); // Токен валиден
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Токен невалиден
    }


}
