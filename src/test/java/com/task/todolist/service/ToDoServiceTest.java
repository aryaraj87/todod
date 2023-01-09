package com.task.todolist.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.task.todolist.enumeration.ItemStatus;
import com.task.todolist.mapper.ItemDtoMapper;
import com.task.todolist.model.Item;
import com.task.todolist.model.dto.ItemDto;
import com.task.todolist.repository.ToDoRepository;


@ExtendWith(MockitoExtension.class)
class ToDoServiceTest {

  
   @InjectMocks
   private ToDoService toDoService;
   
   @Mock 
   ToDoRepository repo;
   
   
   @Mock
   ItemDtoMapper mapper;

    @Test
	void testGetAllItems() {
	    ItemDto newItem = ItemDto.builder()
                .description("Complete assignment")
                .dueDate("2023-01-08T18:50")
                .status("NotDone")
                .build();
	    Item item = Item.builder().creationDate(LocalDateTime.now()).
	    		dueDate(LocalDateTime.parse(newItem.getDueDate())).status(ItemStatus.valueOf
	    				(newItem.getStatus())).build();
	    item.setId(1L);
	    
	    when(repo.findAll()).thenReturn(List.of(item));
	    when(mapper.convertToDtoList(anyList())).thenReturn(List.of(newItem));
        List<ItemDto> itemList = toDoService.getAllItems(null);
        assertThat(itemList).isNotNull();
        assertThat(itemList.size()).isEqualTo(1);

	}
    
    @Test
	void testGetItemDetails() {
	    ItemDto newItem = ItemDto.builder()
                .description("Complete assignment")
                .dueDate("2023-01-08T18:50")
                .status("NotDone")
                .build();
	    
	    Item item = new Item();
	    item.setId(1L);
	    
	    when(repo.findById(anyLong())).thenReturn(Optional.of(item));
	    when(mapper.convertToDto(any(Item.class))).thenReturn(newItem);
        ItemDto itemList = toDoService.getItemDetails(1L);
        assertThat(itemList).isNotNull();
   	}

}
