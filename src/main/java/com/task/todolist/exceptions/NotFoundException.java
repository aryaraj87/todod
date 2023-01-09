package com.task.todolist.exceptions;

/**
 * Exceptions for Not found data related Exception now throws error message
 * saying record not found can be customized later
 *
 */

public class NotFoundException extends RuntimeException {

	public NotFoundException(Long id) {
		super("Record not found : " + id);
	}
}
