package com.mywork.todos.TodosWebServices;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoResource {
	
	@Autowired
	TododsServices todosServices;
	
	@GetMapping(path = "users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username){
		
		return todosServices.findAll();
	
	}
	
	@GetMapping(path = "users/{username}/todos/{id}")
	public Todo getAllTodos(@PathVariable String username, @PathVariable long id){
		
		return todosServices.findById(id);
	
	}
	
	@DeleteMapping(path = "users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id){
	
		Todo todo = todosServices.deleteById(id);
		if(todo!=null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PutMapping(path="users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Todo todo){
		
		Todo updatedTodo = todosServices.updateTodo(todo);
		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
		//return new ResponseEntity<Todo>(updatedTodo, HttpStatus.OK);
		
	}
	
	@PostMapping(path="users/{username}/todos")
	public ResponseEntity<Void> createTodo(@PathVariable String username, @RequestBody Todo todo){
		
		Todo createdTodo = todosServices.updateTodo(todo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
		
	}
	
}
