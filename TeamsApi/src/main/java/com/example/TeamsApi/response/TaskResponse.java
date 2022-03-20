package com.example.TeamsApi.response;

import com.example.TeamsApi.model.Task;
import com.example.TeamsApi.model.User;
import lombok.*;


import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {


    private String title;
    private List<User> users;
    private String taskDescription;
    private Date date;
    private String status;

    public TaskResponse(Task task) {
        this.title = task.getTitle();
        this.users = task.getUsers();
        this.taskDescription = task.getTaskDescription();
        this.date = task.getDate();
        this.status = task.getStatus();
    }
}
