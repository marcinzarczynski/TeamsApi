package com.example.TeamsApi.model;



import lombok.*;

import javax.persistence.*;

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
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    private String name;
    private String lastName;
    private String email;

}
