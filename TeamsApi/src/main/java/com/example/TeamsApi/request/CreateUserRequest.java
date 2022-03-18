package com.example.TeamsApi.request;

import com.example.TeamsApi.model.Task;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "last name is required")
    private String lastName;

    private Task task;

    @NotBlank(message = "email is required")
    private String email;
}
