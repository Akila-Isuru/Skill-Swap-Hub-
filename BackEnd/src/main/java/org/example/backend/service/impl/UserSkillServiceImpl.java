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
    @Transactional
    public void assignSkillToUser(UserSkillDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException("User not found"));

        Skill skill;


        if (dto.getSkillId() != null && dto.getSkillId() > 0) {
            skill = skillRepository.findById(dto.getSkillId())
                    .orElseThrow(() -> new CustomException("Skill not found"));
        } else {

            skill = skillRepository.findBySkillNameIgnoreCase(dto.getSkillName())
                    .orElseGet(() -> {

                        Skill newSkill = new Skill();
                        newSkill.setSkillName(dto.getSkillName());
                        return skillRepository.save(newSkill);
                    });
        }


        UserSkill userSkill = new UserSkill();
        userSkill.setUser(user);
        userSkill.setSkill(skill);
        userSkill.setType(SkillType.valueOf(dto.getType()));
        userSkill.setExpertiseLevel(dto.getExpertiseLevel());

        userSkillRepository.save(userSkill);
    }
    @Override
    public List<UserSkillDTO> getSkillsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));

        List<UserSkill> skills = userSkillRepository.findByUser(user);


        return skills.stream().map(s -> {
            UserSkillDTO dto = new UserSkillDTO();
            dto.setId(s.getId());
            dto.setUserId(s.getUser().getId());
            dto.setSkillId(s.getSkill().getId());
            dto.setSkillName(s.getSkill().getSkillName());
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
    public List<UserSkillDTO> findMatchesForUser(Long userId) {

        List<UserSkill> myLearningSkills = userSkillRepository.findByUser(userRepository.findById(userId).get())
                .stream().filter(s -> s.getType() == SkillType.LEARN).collect(Collectors.toList());

        List<Long> skillIds = myLearningSkills.stream().map(s -> s.getSkill().getId()).collect(Collectors.toList());


        return userSkillRepository.findMatches(skillIds, SkillType.TEACH, userId)
                .stream().map(s -> {
                    UserSkillDTO dto = new UserSkillDTO();
                    dto.setUserId(s.getUser().getId());
                    dto.setUserName(s.getUser().getName());
                    dto.setSkillId(s.getSkill().getId());
                    dto.setSkillName(s.getSkill().getSkillName());

                    return dto;
                }).collect(Collectors.toList());
    }
}