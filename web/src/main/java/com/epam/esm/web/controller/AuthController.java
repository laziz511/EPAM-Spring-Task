package com.epam.esm.web.controller;

import com.epam.esm.core.dto.AuthenticationRequest;
import com.epam.esm.core.dto.AuthenticationResponse;
import com.epam.esm.core.dto.RegisterRequest;
import com.epam.esm.core.entity.User;
import com.epam.esm.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", consumes = "application/json", produces = "application/json")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @Valid @RequestBody RegisterRequest registerRequest) {

        return ResponseEntity.status(201).body(userService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
            @Valid @RequestBody AuthenticationRequest authenticationRequest) {

        AuthenticationResponse tokenResponse = userService.authenticate(authenticationRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshAccessToken(
            HttpServletRequest request, HttpServletResponse response) {

        AuthenticationResponse tokenResponse = userService.refreshToken(request, response);
        return ResponseEntity.ok(tokenResponse);
    }

}