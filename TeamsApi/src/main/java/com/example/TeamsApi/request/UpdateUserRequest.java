package com.example.TeamsApi.request;

import javax.validation.constraints.NotNull;
import lombok.*;




@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotNull(message = "User Id is required")
    private Long userId;
    private String name;
    private String lastName;
    private String email;
}
