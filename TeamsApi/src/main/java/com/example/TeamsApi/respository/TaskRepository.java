package com.example.TeamsApi.respository;

import com.example.TeamsApi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitle(String title);

    Optional<List<Task>> findByStatus(String status);
}
