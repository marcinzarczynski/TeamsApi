package com.example.TeamsApi.response;

import com.example.TeamsApi.model.Task;
import com.example.TeamsApi.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {


    private String title;
    private List<User> user;
    private String taskDescription;
    private Date date;
    private String status;

    public TaskResponse(Task task) {
        this.title = task.getTitle();
        this.user = task.getUser();
        this.taskDescription = task.getTaskDescription();
        this.date = task.getDate();
        this.status = task.getStatus();
    }
}
