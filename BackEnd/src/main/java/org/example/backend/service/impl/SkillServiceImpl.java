package org.example.backend.service.impl;

import org.example.backend.dto.SkillDTO;
import org.example.backend.entity.Skill;
import org.example.backend.exception.CustomException;
import org.example.backend.repository.SkillRepository;
import org.example.backend.repository.UserSkillRepository;
import org.example.backend.service.SkillService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SkillServiceImpl implements SkillService {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserSkillRepository userSkillRepository;

    @Override
    public void saveSkill(SkillDTO skillDTO) {
        if (skillRepository.findBySkillName(skillDTO.getSkillName()).isPresent()) {
            throw new CustomException("Skill already exists!");
        }
        skillRepository.save(modelMapper.map(skillDTO, Skill.class));
    }

    @Override
    public List<SkillDTO> getAllSkills() {
        return modelMapper.map(skillRepository.findAll(), new TypeToken<List<SkillDTO>>() {}.getType());
    }
    @Override
    public List<Map<String, Object>> getTrendingSkills() {
        List<Object[]> results = userSkillRepository.findTrendingSkills();
        List<Map<String, Object>> trendingList = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("skillName", row[0]);
            map.put("count", row[1]);
            trendingList.add(map);
        }
        return trendingList;
    }
}