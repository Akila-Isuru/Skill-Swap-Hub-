package org.example.backend.service.impl;

import org.example.backend.dto.MatchDTO;
import org.example.backend.entity.UserSkill;
import org.example.backend.repository.UserSkillRepository;
import org.example.backend.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MatchServiceImpl implements MatchService {

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Override
    public List<MatchDTO> getMatchesForUser(Long userId) {
        // 1. User ඉගෙන ගන්න කැමති (LEARN) skills ටික ගන්න
        List<UserSkill> userLearningSkills = userSkillRepository.findAll().stream()
                .filter(us -> us.getUser().getId().equals(userId) && us.getType().name().equals("LEARN"))
                .toList();

        List<MatchDTO> matches = new ArrayList<>();

        for (UserSkill learningSkill : userLearningSkills) {
            // 2. ඒ skill එකම TEACH කරන අනිත් අයව database එකෙන් හොයන්න
            List<UserSkill> potentialTeachers = userSkillRepository.findAll().stream()
                    .filter(us -> us.getSkill().getId().equals(learningSkill.getSkill().getId())
                            && us.getType().name().equals("TEACH")
                            && !us.getUser().getId().equals(userId)) // තමන්වම match වෙන්න බැහැ
                    .toList();

            for (UserSkill teacher : potentialTeachers) {
                matches.add(new MatchDTO(
                        teacher.getUser().getId(),
                        teacher.getUser().getName(),
                        teacher.getUser().getEmail(),
                        teacher.getSkill().getSkillName(),
                        teacher.getExpertiseLevel()
                ));
            }
        }
        return matches;
    }
}