package org.example.backend.service;

import org.example.backend.dto.UserDTO;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long id);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
}