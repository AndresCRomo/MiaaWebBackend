package com.web.miaa.services;

import com.web.miaa.models.*;
import com.web.miaa.request.Auth.LoginRequest;
import com.web.miaa.request.Auth.RegisterRequest;
import com.web.miaa.request.Auth.ValidTokenRequest;
import com.web.miaa.response.Auth.AuthResponse;
import com.web.miaa.response.Auth.ValidTokenResponse;
import com.web.miaa.roles.UserRol;

import io.jsonwebtoken.Claims;

import com.web.miaa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        UserDetails userDetails = userRepository.findByUsername(request.getUsername()).orElseThrow();
        UserModel user = (UserModel) userDetails;
        String token = jwtService.getToken(user);
        return AuthResponse.builder().token(token).user(user).build();
    }

    public AuthResponse register(RegisterRequest request) {
        UserModel user = UserModel.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRol.USUARIO)
                .build();
        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
    
    public ValidTokenResponse validToken(ValidTokenRequest request) {

        try {
            UserDetails userDetails = userRepository.findByUsername(jwtService.getUsernameFromToken(request.getToken())).orElseThrow();
            UserModel user = (UserModel) userDetails;

            return ValidTokenResponse.builder()
                    .valid(jwtService.validateToken(request.getToken()))
                    .user(user)
                    .build();
        } catch(Exception e) {
            return ValidTokenResponse.builder()
                    .valid(false)
                    .build();
        }
    }
}
