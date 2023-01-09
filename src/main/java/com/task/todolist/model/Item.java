package com.task.todolist.model;

import java.time.LocalDateTime;

import com.task.todolist.enumeration.ItemStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Item")
public class Item {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String description;
	
	@Column(name="create_date")
	private LocalDateTime creationDate;
	
	@Column(name="due_date")
	private LocalDateTime dueDate;
	
	@Column(name="completed_date")
	private LocalDateTime completedDate;
	
	@Column
	@Enumerated(EnumType.STRING)
	private ItemStatus status;

}
