package com.mini_project.repository;




import com.mini_project.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // findAll(), save(), deleteById()... đã có sẵn!
}
