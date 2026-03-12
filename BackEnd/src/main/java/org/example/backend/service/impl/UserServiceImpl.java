package org.example.backend.service.impl;

import org.example.backend.dto.UserDTO;
import org.example.backend.entity.User;
import org.example.backend.repository.UserRepository;
import org.example.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.backend.exception.CustomException;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveUser(UserDTO userDTO) {
        // අලුත් User කෙනෙක් නිසා Email එක තියෙනවද බලමු
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new CustomException("User already exists with this email!");
        }
        userRepository.save(modelMapper.map(userDTO, User.class));
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        if (!userRepository.existsById(userDTO.getId())) {
            throw new CustomException("User not found!");
        }
        userRepository.save(modelMapper.map(userDTO, User.class));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return modelMapper.map(userRepository.findAll(), new TypeToken<List<UserDTO>>() {}.getType());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("User Not Found"));
        return modelMapper.map(user, UserDTO.class);
    }
}