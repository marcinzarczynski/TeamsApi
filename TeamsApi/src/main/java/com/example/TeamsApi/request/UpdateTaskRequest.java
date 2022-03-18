package com.example.TeamsApi.request;

import com.example.TeamsApi.model.User;
import lombok.*;


import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequest {

    @NotNull(message = "User Id is required")
    private Long taskId;
    private String title;
    private List<User> user;
    private String taskDescription;
    private Date date;
    private String status;
}
