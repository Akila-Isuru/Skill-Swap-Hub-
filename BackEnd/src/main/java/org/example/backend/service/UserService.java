package org.example.backend.service;

import org.example.backend.dto.AuthDTO;
import org.example.backend.dto.AuthResponseDTO;
import org.example.backend.dto.UserDTO;
import org.example.backend.dto.UserSummaryDTO;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long id);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserSummaryDTO getUserSummary(Long userId);
    public List<UserSummaryDTO> getTopRatedUsers();
    public AuthResponseDTO authenticate(AuthDTO authDTO);
}