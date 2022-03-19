package com.example.TeamsApi.controller;

import com.example.TeamsApi.model.User;
import com.example.TeamsApi.request.CreateUserRequest;
import com.example.TeamsApi.request.UpdateUserRequest;
import com.example.TeamsApi.response.UserResponse;
import com.example.TeamsApi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity<>(userService.addUser(createUserRequest), HttpStatus.CREATED);}

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest){
        return userService.updateUser(updateUserRequest).map(m-> new ResponseEntity<>(new UserResponse(m), HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<UserResponse> getByName(@PathVariable String name){
        return userService.findByName(name).map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findByLastName/{lastName}")
    public ResponseEntity<UserResponse> getByLastName(@PathVariable String lastName){
        return userService.findByLastName(lastName).map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<UserResponse> getByEmail(@PathVariable String email){
        return userService.findByEmail(email).map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
