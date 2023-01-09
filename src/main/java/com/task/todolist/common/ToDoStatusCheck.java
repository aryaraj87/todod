package com.task.todolist.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.task.todolist.service.ToDoService;

@Component
public class ToDoStatusCheck {

	private static final Logger log = LoggerFactory.getLogger(ToDoStatusCheck.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	private ToDoService toDoService;

	@Scheduled(fixedRate = 120000)
	public void reportCurrentTime() {
		log.info("The time is now {}", dateFormat.format(new Date()));
		toDoService.updatePastDueItemStatus();
	}

}
