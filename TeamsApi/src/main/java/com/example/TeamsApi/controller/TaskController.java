package com.example.TeamsApi.controller;


import com.example.TeamsApi.request.CreateTaskRequest;
import com.example.TeamsApi.request.UpdateTaskRequest;
import com.example.TeamsApi.response.TaskResponse;
import com.example.TeamsApi.service.TaskService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) { this.taskService = taskService;}

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(){
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> addTasks(@Valid  @RequestBody CreateTaskRequest createTaskRequest){
        return new ResponseEntity<>(taskService.addTask(createTaskRequest), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<TaskResponse> updateTask(@Valid @RequestBody UpdateTaskRequest updateTaskRequest) {
        return taskService.updateTask(updateTaskRequest).map(m -> new ResponseEntity<>(new TaskResponse(m), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findByTitle/{title}")
    public ResponseEntity<TaskResponse> findByTitle(@PathVariable String title){
        return taskService.findByTitle(title).map(u -> new ResponseEntity<>(u, HttpStatus.OK )).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findByDate/{date}")
    public ResponseEntity<TaskResponse> findByDate(@PathVariable Date date){
        return taskService.findByDate(date).map(u-> new ResponseEntity<>(u,HttpStatus.OK)).
                orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findByStatus/{status}")
    public ResponseEntity<TaskResponse> findByStatus(@PathVariable String status){
        return taskService.findByStatus(status).map(u-> new ResponseEntity<>(u,HttpStatus.OK)).
                orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable Long id){
        return taskService.deleteTask(id) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
