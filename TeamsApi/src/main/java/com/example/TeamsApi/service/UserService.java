package com.example.TeamsApi.service;


import com.example.TeamsApi.model.User;
import com.example.TeamsApi.request.CreateUserRequest;
import com.example.TeamsApi.response.UserResponse;
import com.example.TeamsApi.respository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse addUser(final CreateUserRequest createUserRequest) {
        return new UserResponse(userRepository.save(User.builder()
                .name(createUserRequest.getName())
                .lastName(createUserRequest.getLastName())
                .email(createUserRequest.getEmail())
                .build()));
    }

    public List<UserResponse> getAllUsers() {
        List<UserResponse> userResponses = new ArrayList<>();
        userRepository.findAll().forEach(user -> userResponses.add(new UserResponse(user)));
        return userResponses;
    }

    public List<UserResponse> findByName(String name) {
        List<UserResponse> userResponses = new ArrayList<>();
        userRepository.findByName(name)
                .map(t -> {
                    t.forEach(e -> userResponses.add(UserResponse.builder()
                            .name(e.getName())
                            .email(e.getEmail())
                            .lastName(e.getLastName())
                            .build()));
                    return userResponses;
                });
        return userResponses;
    }

    public List<UserResponse> findByLastName(String lastName) {
        List<UserResponse> userResponses = new ArrayList<>();
        userRepository.findByLastName(lastName)
                .map(t -> {
                    t.forEach(e -> userResponses.add(UserResponse.builder()
                            .name(e.getName())
                            .email(e.getEmail())
                            .lastName(e.getLastName())
                            .build()));
                    return userResponses;
                });
        return userResponses;
    }

    public Optional<UserResponse> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserResponse::new);
    }

    public boolean deleteUser(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }

}

