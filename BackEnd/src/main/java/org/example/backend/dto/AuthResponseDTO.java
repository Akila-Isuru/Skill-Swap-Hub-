package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String access_token;
    private Long userId;
    private String userName;
    private String role;
}