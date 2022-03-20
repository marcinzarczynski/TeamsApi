package com.example.TeamsApi.respository;

import com.example.TeamsApi.model.Task;
import com.example.TeamsApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<List<User>> findByName(String name);
    Optional<User> findByLastName(String lastName);
    Optional<User> findByEmail(String email);

}
