package com.task.todolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.todolist.common.CommonConstants;
import com.task.todolist.enumeration.ItemStatus;
import com.task.todolist.model.dto.ItemDto;
import com.task.todolist.service.ToDoService;

@RestController
@RequestMapping("/todo")
public class TodoController {

	@Autowired
	private ToDoService toDoService;

	@PostMapping("/add")
	public ResponseEntity<ItemDto> addTodo(@RequestBody ItemDto toDoDto) {
		return new ResponseEntity<>(toDoService.addItem(toDoDto), HttpStatus.CREATED);

	}

	@PutMapping("/update/description/{id}")
	public ResponseEntity<Object> updateDescription(@RequestBody ItemDto toDoDto, @PathVariable Long id) {
		try {
			return new ResponseEntity<>(toDoService.updateItem(toDoDto, id, CommonConstants.COL_DESC),
					HttpStatus.ACCEPTED);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
	}

	@PutMapping("/update/status/{id}")
	public ResponseEntity<Object> updateStatus(@RequestBody ItemDto toDoDto, @PathVariable Long id) {
		// return toDoService.upDateStatus(toDoDto, id);
		try {
			return new ResponseEntity<>(toDoService.updateItem(toDoDto, id, CommonConstants.COL_STATUS),
					HttpStatus.ACCEPTED);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);

		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getTodoDetails(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(toDoService.getItemDetails(id), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
	}

	@GetMapping("/all")
	public List<ItemDto> getAllTodo(@RequestParam(required = false) ItemStatus status) {
		return toDoService.getAllItems(status);
	}
}
