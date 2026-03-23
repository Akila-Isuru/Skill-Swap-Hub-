package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.AuthDTO;
import org.example.backend.dto.AuthResponseDTO;
import org.example.backend.dto.UserDTO;
import org.example.backend.service.UserService;
import org.example.backend.utill.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<String>> register(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return new ResponseEntity<>(new APIResponse<>(201, "Registration Successful", null), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<AuthResponseDTO>> login(@RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(new APIResponse<>(200, "Login Success", userService.authenticate(authDTO)));
    }
}