package com.example.TeamsApi.service;


import com.example.TeamsApi.model.User;
import com.example.TeamsApi.request.CreateUserRequest;
import com.example.TeamsApi.request.UpdateUserRequest;
import com.example.TeamsApi.response.UserResponse;
import com.example.TeamsApi.respository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository;}

    public User saveUser(User user){ return userRepository.save(user);}

    public UserResponse addUser(final CreateUserRequest createUserRequest){
        return new UserResponse(userRepository.save(User.builder()
                .name(createUserRequest.getName())
                .lastName(createUserRequest.getLastName())
                .task(createUserRequest.getTask())
                .email(createUserRequest.getEmail())
                .build()));
    }

    public Optional<User> updateUser(UpdateUserRequest updateUserRequest) {
        return userRepository.findById(updateUserRequest.getUserId()).
                map(m -> updateUser(m, updateUserRequest));
    }

    private User updateUser(User user, UpdateUserRequest updateUserRequest) {
        user.setTask(updateUserRequest.getTask());
        user.setName(updateUserRequest.getName());
        user.setLastName(updateUserRequest.getLastName());
        user.setEmail(updateUserRequest.getEmail());
        return saveUser(user);
    }

    public List<UserResponse> getAllUsers(){
        List<UserResponse> userResponses = new ArrayList<>();
        userRepository.findAll().forEach(user-> userResponses.add(new UserResponse(user)));
        return userResponses;
    }

    public Optional<UserResponse> findByName(String name){
        return userRepository.findByName(name).map(UserResponse::new);
    }

    public Optional<UserResponse> findByLastName(String lastName){
        return userRepository.findByLastName(lastName).map(UserResponse::new);
    }

    public Optional<UserResponse> findByEmail(String email){
        return userRepository.findByEmail(email).map(UserResponse::new);
    }

    public boolean deleteUser(Long id){
        return userRepository.findById(id).map(m-> {
            userRepository.delete(m);
            return true;
        }).orElse(false);
    }

}

