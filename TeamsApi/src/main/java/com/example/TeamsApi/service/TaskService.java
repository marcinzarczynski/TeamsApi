package com.example.TeamsApi.service;

import com.example.TeamsApi.model.Task;
import com.example.TeamsApi.request.CreateTaskRequest;
import com.example.TeamsApi.request.UpdateTaskRequest;
import com.example.TeamsApi.response.TaskResponse;
import com.example.TeamsApi.respository.TaskRepository;
import com.example.TeamsApi.respository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public List<TaskResponse> getAllTasks() {
        List<TaskResponse> taskResponses = new ArrayList<>();
        taskRepository.findAll().forEach(task -> taskResponses.add(new TaskResponse(task)));
        return taskResponses;
    }

    public TaskResponse addTask(final CreateTaskRequest createTaskRequest) {
        return new TaskResponse(taskRepository.save(Task.builder()
                .title(createTaskRequest.getTitle())
                .users(createTaskRequest.getUser())
                .taskDescription(createTaskRequest.getTaskDescription())
                .date(createTaskRequest.getDate())
                .status(createTaskRequest.getStatus())
                .build()));
    }

    public Optional<Task> updateTask(UpdateTaskRequest updateTaskRequest) {
        return taskRepository.findById(updateTaskRequest.getTaskId()).
                map(m -> updateTask(m, updateTaskRequest));
    }

    private Task updateTask(Task task, UpdateTaskRequest updateTaskRequest) {
        task.setTitle(updateTaskRequest.getTitle());
        task.setUsers(updateTaskRequest.getUser());
        task.setTaskDescription(updateTaskRequest.getTaskDescription());
        task.setDate(updateTaskRequest.getDate());
        task.setStatus(updateTaskRequest.getStatus());
        return saveTask(task);
    }

    public Optional<TaskResponse> findByTitle(String title) {
        return taskRepository.findByTitle(title).map(TaskResponse::new);
    }

    public Optional<TaskResponse> findByDate(Date date) {
        return taskRepository.findByDate(date).map(TaskResponse::new);
    }

    public Optional<TaskResponse> findByStatus(String status) {
        return taskRepository.findByStatus(status).map(TaskResponse::new);
    }

    public boolean deleteTask(Long id) {
        return taskRepository.findById(id).map(m -> {
            taskRepository.delete(m);
            return true;
        }).orElse(false);
    }

    public Optional<TaskResponse> assignTask(String email, String taskTitle) {
        var user = userRepository.findByEmail(email);
        var task = taskRepository.findByTitle(taskTitle);

        if (user.isPresent() && task.isPresent()) {
            task.get().getUsers().add(user.get());
            var assignTask = taskRepository.save(task.get());
            return Optional.of(TaskResponse.builder()
                    .title(assignTask.getTitle())
                    .date(assignTask.getDate())
                    .status(assignTask.getStatus())
                    .taskDescription(assignTask.getTaskDescription())
                    .users(assignTask.getUsers()).build());
        }
        return Optional.empty();
    }

}
