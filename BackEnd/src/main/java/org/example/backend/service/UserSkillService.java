package org.example.backend.service;

import org.example.backend.dto.UserSkillDTO;

import java.util.List;

public interface UserSkillService {
    void assignSkillToUser(UserSkillDTO userSkillDTO);
    List<UserSkillDTO> getSkillsByUserId(Long userId);
    void deleteUserSkill(Long id);
    List<UserSkillDTO> findMatchesForUser(Long userId);
}