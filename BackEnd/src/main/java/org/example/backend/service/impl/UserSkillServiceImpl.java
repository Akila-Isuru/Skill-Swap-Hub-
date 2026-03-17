package org.example.backend.service.impl;

import org.example.backend.dto.UserSkillDTO;
import org.example.backend.entity.Skill;
import org.example.backend.entity.enums.SkillType;
import org.example.backend.entity.User;
import org.example.backend.entity.UserSkill;
import org.example.backend.exception.CustomException;
import org.example.backend.repository.SkillRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.repository.UserSkillRepository;
import org.example.backend.service.UserSkillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserSkillServiceImpl implements UserSkillService {

    @Autowired
    private UserSkillRepository userSkillRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void assignSkillToUser(UserSkillDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException("User not found"));
        Skill skill = skillRepository.findById(dto.getSkillId())
                .orElseThrow(() -> new CustomException("Skill not found"));

        UserSkill userSkill = new UserSkill();
        userSkill.setUser(user);
        userSkill.setSkill(skill);
        userSkill.setType(SkillType.valueOf(dto.getType())); // Enum එකට convert කිරීම
        userSkill.setExpertiseLevel(dto.getExpertiseLevel());

        userSkillRepository.save(userSkill);
    }

    @Override
    public List<UserSkillDTO> getSkillsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));

        List<UserSkill> skills = userSkillRepository.findByUser(user);

        // Manual mapping for DTO (Because of ID mapping)
        return skills.stream().map(s -> {
            UserSkillDTO dto = new UserSkillDTO();
            dto.setId(s.getId());
            dto.setUserId(s.getUser().getId());
            dto.setSkillId(s.getSkill().getId());
            dto.setType(s.getType().name());
            dto.setExpertiseLevel(s.getExpertiseLevel());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteUserSkill(Long id) {
        if (!userSkillRepository.existsById(id)) {
            throw new CustomException("Skill mapping not found!");
        }
        userSkillRepository.deleteById(id);
    }
}