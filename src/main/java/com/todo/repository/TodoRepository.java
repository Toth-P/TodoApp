package com.todo.repository;

import java.util.List;

import com.todo.model.Todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer>{

    List<Todo> getByCompleted(Boolean completed);

}
