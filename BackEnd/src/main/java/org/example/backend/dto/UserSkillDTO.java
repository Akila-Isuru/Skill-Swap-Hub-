package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSkillDTO {
    private Long id;
    private Long userId;
    private Long skillId;
    private String skillName;
    private String type;
    private String expertiseLevel;
}