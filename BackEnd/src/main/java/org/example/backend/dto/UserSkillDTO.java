package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSkillDTO {
    private Long id;
    private Long userId;   // User ගේ ID එක
    private Long skillId;  // Skill එකේ ID එක
    private String type;    // TEACH හෝ LEARN
    private String expertiseLevel; // Beginner, Intermediate, Expert
}