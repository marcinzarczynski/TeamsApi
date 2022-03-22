package com.example.TeamsApi.service;

import com.example.TeamsApi.model.Task;
import com.example.TeamsApi.model.User;
import com.example.TeamsApi.request.CreateTaskRequest;
import com.example.TeamsApi.request.UpdateTaskRequest;
import com.example.TeamsApi.response.TaskResponse;
import com.example.TeamsApi.respository.TaskRepository;
import com.example.TeamsApi.respository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public Optional<Task> updateTask(UpdateTaskRequest updateTaskRequest, String title) {
        return taskRepository.findByTitle(title).
                map(task -> updateTask(task, updateTaskRequest));
    }

    private Task updateTask(Task task, UpdateTaskRequest updateTaskRequest) {
        task.setTitle(updateTaskRequest.getTitle());
        task.setTaskDescription(updateTaskRequest.getTaskDescription());
        task.setDate(updateTaskRequest.getDate());
        task.setStatus(updateTaskRequest.getStatus());
        return saveTask(task);
    }

    public Optional<TaskResponse> findByTitle(String title) {
        return taskRepository.findByTitle(title).map(TaskResponse::new);
    }

    public List<TaskResponse> findByStatus(String status) {
        List<TaskResponse> taskResponses = new ArrayList<>();
        taskRepository.findByStatus(status)
                .map(t -> {
                 t.forEach(e -> taskResponses.add(TaskResponse.builder()
                         .title(e.getTitle())
                         .date(e.getDate())
                         .status(e.getStatus())
                         .taskDescription(e.getTaskDescription())
                         .users(e.getUsers())
                         .build()));
                 return taskResponses;
                });
        return taskResponses;
    }

    public boolean deleteTask(Long id) {
        return taskRepository.findById(id).map(task -> {
            taskRepository.delete(task);
            return true;
        }).orElse(false);
    }

    public Optional<TaskResponse> assignTask(String email, String taskTitle) {
        var user = userRepository.findByEmail(email);
        var task = taskRepository.findByTitle(taskTitle);

        return user.isPresent() && task.isPresent() ? assignUserToTask(user.get(), task.get())
                : Optional.empty();
    }

    @NotNull
    private Optional<TaskResponse> assignUserToTask(User user, Task task) {
        task.getUsers().add(user);
        var assignTask = taskRepository.save(task);
        return Optional.of(TaskResponse.builder()
                .title(assignTask.getTitle())
                .date(assignTask.getDate())
                .status(assignTask.getStatus())
                .taskDescription(assignTask.getTaskDescription())
                .users(assignTask.getUsers()).build());
    }

}
