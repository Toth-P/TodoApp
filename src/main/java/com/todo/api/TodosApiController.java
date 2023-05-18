package com.todo.api;

import com.todo.model.Todo;
import com.todo.model.TodoRequestDTO;
import com.todo.service.TodoService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TodosApiController {
    @Autowired
    TodoService todoService;

    @GetMapping("/todo")
    public ResponseEntity<List<Todo>> getTodos(@RequestParam(value = "completed", required = false, defaultValue = "false") @Valid Boolean completed) {
        //Todo all todos can be requested at once
        return ResponseEntity.ok(todoService.getByCompleted(completed));
    }


    @PostMapping("/todo")
    public ResponseEntity<Void> createTodo(@Valid @RequestBody TodoRequestDTO todo, @RequestHeader(value = "token") String token) {
        todoService.createTodo(todo, token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<Todo> getTodo(@ApiParam(value = "A unique identifier for a `todo`.", required = true) @PathVariable("todoId") Integer todoId) {
        return ResponseEntity.ok(todoService.findById(todoId));
    }


    @PutMapping("/todo/{todoId}")
    public ResponseEntity<Void> updateTodo(@ApiParam(value = "A unique identifier for a `todo`.", required = true) @PathVariable("todoId") Integer todoId, @ApiParam(value = "Updated `todo` information.", required = true) @Valid @RequestBody TodoRequestDTO todo, @RequestHeader(value = "token") String token) {
        todoService.updateTodo(todoId, todo, token);
        return ResponseEntity.accepted().build();
    }


    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<Void> deleteTodo(@ApiParam(value = "A unique identifier for a `todo`.", required = true) @PathVariable("todoId") Integer todoId) {
        todoService.deleteById(todoId);
        return ResponseEntity.noContent().build();
    }
}
