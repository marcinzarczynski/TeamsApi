package com.example.TeamsApi.request;

import com.example.TeamsApi.model.Task;
import javax.validation.constraints.NotNull;

import com.example.TeamsApi.model.User;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotNull(message = "User Id is required")
    private Long userId;
    private List<Task> task;
    private String name;
    private String lastName;
    private String email;
}
