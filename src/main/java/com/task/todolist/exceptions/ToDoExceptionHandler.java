package com.task.todolist.exceptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception Handled for Bank Application
 *
 */
@ControllerAdvice
@Slf4j
public class ToDoExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	protected ResponseEntity<List> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {

		log.info("Exceptions");
		List list = ex.getBindingResult().getAllErrors().stream().map(fieldError -> fieldError.getDefaultMessage())
				.collect(Collectors.toList());

		return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidFormatException.class)
	@ResponseBody
	public ResponseEntity<String> invalidFormatException(final InvalidFormatException e) {
		return new ResponseEntity<>(e.getMessage().substring(e.getMessage().indexOf(":")), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<ErrorResponse> springHandleNotFound(NotFoundException e, WebRequest request)
			throws IOException {
		ErrorResponse error = new ErrorResponse(new Date(), e.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

}
