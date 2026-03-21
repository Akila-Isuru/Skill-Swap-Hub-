package org.example.backend.controller;

import org.example.backend.dto.UserSkillDTO;
import org.example.backend.service.UserSkillService;
import org.example.backend.utill.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/user-skill")
public class UserSkillController {

    @Autowired
    private UserSkillService userSkillService;

    @PostMapping("/assign")
    public ResponseEntity<APIResponse<String>> assignSkill(@RequestBody UserSkillDTO dto) {
        userSkillService.assignSkillToUser(dto);
        return new ResponseEntity<>(new APIResponse<>(201, "Skill assigned to user successfully", null), HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<APIResponse<List<UserSkillDTO>>> getSkills(@PathVariable Long id) {
        return new ResponseEntity<>(new APIResponse<>(200, "Success", userSkillService.getSkillsByUserId(id)), HttpStatus.OK);
    }
    @GetMapping("/matches/{userId}")
    public ResponseEntity<APIResponse<List<UserSkillDTO>>> getMatches(@PathVariable Long userId) {
        // මම කලින් දීපු logic එක පාවිච්චි කරලා මෙතන Service එක call කරන්න
        return new ResponseEntity<>(new APIResponse<>(200, "Matches found", userSkillService.findMatchesForUser(userId)), HttpStatus.OK);
    }
}