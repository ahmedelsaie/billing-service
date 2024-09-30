package com.bill.controller;

import com.bill.dtos.user.LoginResponse;
import com.bill.dtos.user.LoginUserDto;
import com.bill.dtos.user.RegisterUserDto;
import com.bill.model.user.User;
import com.bill.services.user.AuthenticationService;
import com.bill.services.user.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public User register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return authenticationService.signup(registerUserDto);
    }

    @PostMapping("/login")
    public LoginResponse authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        return new LoginResponse().setToken(jwtToken).
                setExpiresIn(jwtService.getExpirationTime());
    }
}