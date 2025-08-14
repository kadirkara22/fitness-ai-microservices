package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private  final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    public UserResponse register(@Valid RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
      throw new RuntimeException("Email already exist");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        User savedUser=userRepository.save(user);

        return mapToResponse(savedUser);
    }

    public UserResponse getUserProfile(String userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User Not Found"));

        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }



    public Boolean existByUserId(String userId) {
        log.info("Calling User Validation API for userId: {}", userId);
    return userRepository.existsById(userId);
    }
}
