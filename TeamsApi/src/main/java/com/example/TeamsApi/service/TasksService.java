package com.example.TeamsApi.service;


import com.example.TeamsApi.model.Task;
import com.example.TeamsApi.response.TaskResponse;
import com.example.TeamsApi.respository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TasksService {

    private final TaskRepository taskRepository;

    public TasksService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;}

    public Task createTask(Task task){
        return taskRepository.save(task);}

    public List<Task> getTasks(){
        return taskRepository.findAll();}

    public Optional<TaskResponse> findByTitle(String title){
        return taskRepository.findByTitle(title).map(TaskResponse::new);
    }

    public Optional<TaskResponse> findByDate(Date date){
        return taskRepository.findByDate(date).map(TaskResponse::new);
    }

    public Optional<TaskResponse> findByStatus(String status){
        return taskRepository.findByStatus(status).map(TaskResponse::new);
    }

    public boolean deleteTask(Long id){
        return taskRepository.findById(id).map(m-> {
            taskRepository.delete(m);
            return true;
        }).orElse(false);
    }
}
