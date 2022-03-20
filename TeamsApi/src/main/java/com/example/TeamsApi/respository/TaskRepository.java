package com.example.TeamsApi.respository;

import com.example.TeamsApi.model.Task;
import com.example.TeamsApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    Optional<Task> findByTitle(String title);
    Optional<Task> findByDate(Date date);
    Optional<Task>  findByStatus(String status);
}
