package com.monal.driveEase.Services.Impl;

import com.monal.driveEase.DTOs.Request.LoginRequest;
import com.monal.driveEase.DTOs.Request.RegisterRequest;
import com.monal.driveEase.DTOs.Response.AuthResponse;
import com.monal.driveEase.Entities.User;
import com.monal.driveEase.Repositories.UserRepository;
import com.monal.driveEase.Services.AuthService;
import com.monal.driveEase.enums.Role;
import com.monal.driveEase.exception.BadRequestException;
import com.monal.driveEase.exception.ResourceNotFoundException;
import com.monal.driveEase.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {

       // System.out.println("1. Registration started");

       // System.out.println("2. Checking email...");
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }
       // System.out.println("3. Email check completed");

        if (request.getRole() == Role.ADMIN) {
            throw new BadRequestException(
                    "Admin registration is not allowed"
            );
        }

       // System.out.println("4. Starting password encoding...");
        String encodedPassword = passwordEncoder.encode(request.getPassword());
      //  System.out.println("5. Password encoding completed");

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodedPassword)
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .role(request.getRole())
                .build();

     //   System.out.println("6. Saving user...");
        User savedUser = userRepository.save(user);
     //   System.out.println("7. User saved");

      //  System.out.println("8. Generating JWT...");
        String token = jwtService.generateToken(savedUser);
        //System.out.println("9. JWT generated");

        return AuthResponse.builder()
                .token(token)
                .message("Registration Successful")
                .role(savedUser.getRole().name())
                .userId(savedUser.getId())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .message("Login Successful")
                .role(user.getRole().name())
                .userId(user.getId())
                .build();
    }
}