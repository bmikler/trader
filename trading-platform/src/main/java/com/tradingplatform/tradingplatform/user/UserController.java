package com.tradingplatform.tradingplatform.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
class UserController {

    private final UserService userService;

    @PostMapping
    ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse response = userService.createUser(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}

record RegisterRequest(@Email String email, @NotBlank @Size(min = 6) String password) { }

record RegisterResponse(UUID id, String email) {}
