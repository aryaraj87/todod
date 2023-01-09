package com.task.todolist.exceptions;

import com.task.todolist.enumeration.ItemStatus;

/**
 * Exceptions for Not found data related
 * Exception now throws error message saying record not found can be customized later
 *
 */


public class NotAllowed extends RuntimeException {

    public NotAllowed(Long id) {
        super("Updation of todo items for past time not allowed " + id);
    }
    
	public NotAllowed(ItemStatus status) {
        super("You can not update the satatus to PAST_DUE ");
    }
}
