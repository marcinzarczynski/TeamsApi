package com.example.TeamsApi.response;

import com.example.TeamsApi.model.Task;
import com.example.TeamsApi.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {


    private Task task;
    private String name;
    private String lastName;
    private String email;

    public UserResponse(User user) {
        this.task = user.getTask();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}
