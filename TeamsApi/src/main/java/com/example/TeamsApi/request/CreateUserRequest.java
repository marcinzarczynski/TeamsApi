package com.example.TeamsApi.request;

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

    @NotBlank(message = "email is required")
    private String email;
}
