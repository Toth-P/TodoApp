package com.todo.service;

import com.todo.model.Todo;
import com.todo.model.TodoRequestDTO;
import com.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    UserService userService;

    public void createTodo(TodoRequestDTO todo, String token) {
        todoRepository.save(new Todo(todo, userService.getUserByToken(token)));
    }

    public List<Todo> getByCompleted(Boolean completed) {
        return todoRepository.getByCompleted(completed);
    }

    public Todo findById(Integer todoId) {
        return todoRepository.findById(todoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void updateTodo(Integer todoId, TodoRequestDTO todo, String token) {
        Todo actualTodo = todoRepository.findById(todoId).orElseThrow(() -> new RuntimeException("Id not found"));
        actualTodo.setName(todo.getName());
        actualTodo.setDescription(todo.getDescription());
        actualTodo.setDate(todo.getDate());
        actualTodo.setCompleted(todo.getCompleted());
        actualTodo.setUser(userService.getUserByToken(token));
        todoRepository.save(actualTodo);
    }

    public void deleteById(Integer todoId) {
        todoRepository.deleteById(todoId);
    }
}
