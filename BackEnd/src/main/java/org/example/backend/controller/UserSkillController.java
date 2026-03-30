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

        return new ResponseEntity<>(new APIResponse<>(200, "Matches found", userSkillService.findMatchesForUser(userId)), HttpStatus.OK);
    }
    @GetMapping("/by-skill/{skillId}")
    public ResponseEntity<APIResponse<List<UserSkillDTO>>> getUsersTeachingSkill(
            @PathVariable Long skillId) {
        List<UserSkillDTO> result = userSkillService.getUsersTeachingSkill(skillId);
        return ResponseEntity.ok(new APIResponse<>(200, "Success", result));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteUserSkill(@PathVariable Long id) {
        userSkillService.deleteUserSkill(id);
        return new ResponseEntity<>(new APIResponse<>(200, "Skill removed successfully", null), HttpStatus.OK);
    }
}