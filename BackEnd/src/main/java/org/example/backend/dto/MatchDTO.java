package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MatchDTO {
    private Long userId;
    private String userName;
    private String email;
    private String skillName;
    private String expertiseLevel;
}