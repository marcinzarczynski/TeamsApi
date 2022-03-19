package com.example.TeamsApi.request;

import com.example.TeamsApi.model.Task;
import com.example.TeamsApi.model.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "name is required")
    private String name;

    private List<Task> task;

    @NotBlank(message = "last name is required")
    private String lastName;

    @NotBlank(message = "email is required")
    private String email;
}
