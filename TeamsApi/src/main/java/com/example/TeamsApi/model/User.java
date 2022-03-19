package com.example.TeamsApi.model;



import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Task> task;
    private String name;
    private String lastName;
    private String email;

}
