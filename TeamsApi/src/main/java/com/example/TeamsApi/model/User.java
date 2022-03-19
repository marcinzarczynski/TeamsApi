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
    private String name;
    private String lastName;
    private String email;

}
