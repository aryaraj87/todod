package com.task.todolist.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response Data need to be shown
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

	  private Date timestamp;
	  private String message;
	  private String details;
}
