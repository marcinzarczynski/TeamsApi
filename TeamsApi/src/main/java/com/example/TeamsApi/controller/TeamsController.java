package com.example.TeamsApi.controller;


import com.example.TeamsApi.request.CreateTaskRequest;
import com.example.TeamsApi.request.CreateUserRequest;
import com.example.TeamsApi.request.UpdateTaskRequest;
import com.example.TeamsApi.response.TaskResponse;
import com.example.TeamsApi.response.UserResponse;
import com.example.TeamsApi.service.TaskService;
import com.example.TeamsApi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamsController {

    private final TaskService taskService;
    private final UserService userService;

    public TeamsController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/task")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @PostMapping("/task")
    public ResponseEntity<TaskResponse> addTask(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        return new ResponseEntity<>(taskService.addTask(createTaskRequest), HttpStatus.CREATED);
    }

    @PutMapping("/task/{title}")
    public ResponseEntity<TaskResponse> updateTask(@Valid @RequestBody UpdateTaskRequest updateTaskRequest, @PathVariable String title) {
        return taskService.updateTask(updateTaskRequest, title).map(m -> new ResponseEntity<>(new TaskResponse(m), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/task/findByTitle/{title}")
    public ResponseEntity<TaskResponse> findByTitle(@PathVariable String title) {
        return taskService.findByTitle(title).map(u -> new ResponseEntity<>(u, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/task/findByStatus/{status}")
    public ResponseEntity<List<TaskResponse>> findByStatus(@PathVariable String status) {
        var taskResponses = taskService.findByStatus(status);
        return taskResponses.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(taskResponses, HttpStatus.OK);
    }

    @DeleteMapping("/task/deleteTask/{id}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/assignTask/{email}/{taskTitle}")
    public ResponseEntity<TaskResponse> assignTask(@PathVariable String email, @PathVariable String taskTitle) {
        return taskService.assignTask(email, taskTitle).map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return new ResponseEntity<>(userService.addUser(createUserRequest), HttpStatus.CREATED);
    }

    @GetMapping("/user/findByName/{name}")
    public ResponseEntity<List<UserResponse>> findByName(@PathVariable String name) {
        var userResponses = userService.findByName(name);
        return userResponses.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @GetMapping("/user/findByLastName/{lastName}")
    public ResponseEntity<List<UserResponse>> findByLastName(@PathVariable String lastName) {
        var userResponses = userService.findByLastName(lastName);
        return userResponses.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @GetMapping("/user/findByEmail/{email}")
    public ResponseEntity<UserResponse> findByEmail(@PathVariable String email) {
        return userService.findByEmail(email).map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/user/deleteUser/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
