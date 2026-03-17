package org.example.backend.service.impl;

import org.example.backend.dto.UserDTO;
import org.example.backend.dto.UserSummaryDTO;
import org.example.backend.entity.User;
import org.example.backend.entity.enums.RequestStatus;
import org.example.backend.repository.SwapRequestRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.repository.UserSkillRepository;
import org.example.backend.service.ReviewService;
import org.example.backend.service.UserService;
import org.example.backend.service.UserSkillService;
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
    private ReviewService reviewService;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private SwapRequestRepository swapRequestRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveUser(UserDTO userDTO) {

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
    @Override
    public UserSummaryDTO getUserSummary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));

        UserSummaryDTO summary = new UserSummaryDTO();
        summary.setUserId(user.getId());
        summary.setUserName(user.getName());
        summary.setEmail(user.getEmail());
        summary.setBio(user.getBio());


        summary.setAverageRating(reviewService.getAverageRating(userId));


        long skillCount = userSkillRepository.findByUser(user).size();
        summary.setTotalSkills(skillCount);


        long pendingIncoming = swapRequestRepository.findByReceiverIdAndStatus(userId, RequestStatus.PENDING).size();
        summary.setPendingIncomingRequests(pendingIncoming);


        long totalOutgoing = swapRequestRepository.findBySenderId(userId).size();
        summary.setTotalOutgoingRequests(totalOutgoing);

        return summary;
    }
}