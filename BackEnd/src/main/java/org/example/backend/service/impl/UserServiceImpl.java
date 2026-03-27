package org.example.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.*;
import org.example.backend.entity.User;
import org.example.backend.entity.enums.RequestStatus;
import org.example.backend.entity.enums.Role;
import org.example.backend.repository.*;
import org.example.backend.service.ReviewService;
import org.example.backend.service.UserService;
import org.example.backend.utill.JwtUtil;
import org.example.backend.exception.CustomException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor // Autowired වෙනුවට මේක පාවිච්චි කරමු
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReviewService reviewService;
    private final UserSkillRepository userSkillRepository;
    private final SwapRequestRepository swapRequestRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void saveUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new CustomException("User already exists with this email!");
        }

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .bio(userDTO.getBio())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    @Override
    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        User user = userRepository.findByEmail(authDTO.getEmail())
                .orElseThrow(() -> new CustomException("User not found"));

        if (!passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
            throw new CustomException("Invalid credentials!");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponseDTO(token, user.getId(), user.getName(), user.getRole().name());
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User existUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new CustomException("User not found!"));

        existUser.setName(userDTO.getName());
        existUser.setEmail(userDTO.getEmail());
        existUser.setBio(userDTO.getBio());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(existUser);
    }

    @Override
    public void deleteUser(Long id) { userRepository.deleteById(id); }

    @Override
    public List<UserDTO> getAllUsers() {
        return modelMapper.map(userRepository.findAll(), new TypeToken<List<UserDTO>>() {}.getType());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("User Not Found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserSummaryDTO getUserSummary(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException("User not found"));
        UserSummaryDTO summary = new UserSummaryDTO();
        summary.setUserId(user.getId());
        summary.setUserName(user.getName());
        summary.setEmail(user.getEmail());
        summary.setBio(user.getBio());
        summary.setAverageRating(reviewService.getAverageRating(userId));
        summary.setTotalSkills((long) userSkillRepository.findByUser(user).size());
        summary.setPendingIncomingRequests((long) swapRequestRepository.findByReceiverIdAndStatus(userId, RequestStatus.PENDING).size());
        summary.setTotalOutgoingRequests((long) swapRequestRepository.findBySenderId(userId).size());
        return summary;
    }

    @Override
    public List<UserSummaryDTO> getTopRatedUsers() {
        return userRepository.findAll().stream()
                .map(user -> getUserSummary(user.getId()))

                .filter(summary -> summary.getAverageRating() > 0)

                .sorted((u1, u2) -> Double.compare(u2.getAverageRating(), u1.getAverageRating()))
                .limit(5)
                .collect(Collectors.toList());
    }
}