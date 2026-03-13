package org.example.backend.controller;

import org.example.backend.dto.MatchDTO;
import org.example.backend.service.MatchService;
import org.example.backend.utill.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse<List<MatchDTO>>> findMatches(@PathVariable Long userId) {
        List<MatchDTO> matches = matchService.getMatchesForUser(userId);
        return new ResponseEntity<>(new APIResponse<>(200, "Matches found successfully", matches), HttpStatus.OK);
    }
}