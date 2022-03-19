package com.example.TeamsApi.response;

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


    private String name;
    private String lastName;
    private String email;

    public UserResponse(User user) {
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}
