package com.example.TeamsApi.request;

import com.example.TeamsApi.model.Task;
import com.example.TeamsApi.model.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {

    @NotBlank(message = "title is required")
    private String title;

    private List<User> user;

    @NotBlank(message = "task description is required")
    private String taskDescription ;

    @NotNull(message = "date is required")
    private Date date;

    @NotBlank(message = "status is required")
    private String status;


}
