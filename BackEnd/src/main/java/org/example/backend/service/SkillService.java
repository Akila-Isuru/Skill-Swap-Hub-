package org.example.backend.service;

import org.example.backend.dto.SkillDTO;

import java.util.List;

public interface SkillService {
    void saveSkill(SkillDTO skillDTO);
    List<SkillDTO> getAllSkills();
}