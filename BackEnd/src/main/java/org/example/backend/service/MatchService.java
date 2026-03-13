package org.example.backend.service;

import org.example.backend.dto.MatchDTO;

import java.util.List;

public interface MatchService {
    List<MatchDTO> getMatchesForUser(Long userId);
}