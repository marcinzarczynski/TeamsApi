package com.example.TeamsApi.response;

import com.example.TeamsApi.model.User;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long userId;
    private String name;
    private String lastName;
    private String email;

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }


}
