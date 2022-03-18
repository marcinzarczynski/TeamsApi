package com.example.TeamsApi.controller;


import brave.Response;
import com.example.TeamsApi.model.Task;
import com.example.TeamsApi.response.TaskResponse;
import com.example.TeamsApi.service.TasksService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TasksService tasksService;

    public TaskController(TasksService tasksService) { this.tasksService = tasksService;}

    @GetMapping
    List<Task> getTasks(){ return tasksService.getTasks();}

    @PostMapping
    Task createTasks(@RequestBody Task task){ return tasksService.createTask(task);}

    @GetMapping("/findByTitle/{title}")
    public ResponseEntity<TaskResponse> findByTitle(@PathVariable String title){
        return tasksService.findByTitle(title).map(u -> new ResponseEntity<>(u, HttpStatus.OK )).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findByDate/{date}")
    public ResponseEntity<TaskResponse> findByDate(@PathVariable Date date){
        return tasksService.findByDate(date).map(u-> new ResponseEntity<>(u,HttpStatus.OK)).
                orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findByStatus/{status}")
    public ResponseEntity<TaskResponse> findByStatus(@PathVariable String status){
        return tasksService.findByStatus(status).map(u-> new ResponseEntity<>(u,HttpStatus.OK)).
                orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable Long id){
        return tasksService.deleteTask(id) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
