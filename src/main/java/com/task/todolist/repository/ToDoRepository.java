package com.task.todolist.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.task.todolist.enumeration.ItemStatus;
import com.task.todolist.model.Item;

@Repository
public interface ToDoRepository extends JpaRepository<Item, Long> {
	
	List<Item> findAllByStatus(ItemStatus status);
	
	//List<Item> findAllByStatusAndDueDateGreaterThan(ItemStatus status,LocalDateTime dueDate);
	
	@Query(value = "SELECT * FROM ITEM WHERE STATUS = ?1 AND DUE_DATE < ?2 ",nativeQuery = true)
    List<Item> findAllByStatusAndDueDate(String status,LocalDateTime dueDate);


}
