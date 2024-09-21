package org.example.bookservice.cloud;

import org.example.bookservice.dto.JwtAuthResponse;
import org.example.bookservice.dto.SignInRequest;
import org.example.bookservice.dto.SignUpRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", url = "http://localhost:8082")
public interface AuthClient {

    @PostMapping("/auth/sign-in")
    JwtAuthResponse signIn(SignInRequest request);

    @PostMapping("/auth/sign-up")
    JwtAuthResponse signUp(SignUpRequest request);

    //TODO: ????
    @GetMapping("/auth/validate-token")
    ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String token);

}