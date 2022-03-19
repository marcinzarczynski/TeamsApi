package com.example.TeamsApi.response;

import com.example.TeamsApi.model.Task;
import com.example.TeamsApi.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {


    private String name;
    private String lastName;
    private String email;
    private List<Task> task;

    public UserResponse(User user) {
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}
