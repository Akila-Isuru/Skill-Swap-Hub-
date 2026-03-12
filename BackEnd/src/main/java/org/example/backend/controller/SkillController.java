package org.example.backend.controller;

import org.example.backend.dto.SkillDTO;
import org.example.backend.service.SkillService;
import org.example.backend.utill.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/skill")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @PostMapping
    public ResponseEntity<APIResponse<String>> saveSkill(@RequestBody SkillDTO skillDTO) {
        skillService.saveSkill(skillDTO);
        return new ResponseEntity<>(new APIResponse<>(201, "Skill Saved", null), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<APIResponse<List<SkillDTO>>> getAll() {
        return new ResponseEntity<>(new APIResponse<>(200, "Success", skillService.getAllSkills()), HttpStatus.OK);
    }
}