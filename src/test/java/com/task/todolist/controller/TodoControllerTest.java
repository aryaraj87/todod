package com.task.todolist.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.todolist.model.dto.ItemDto;
import com.task.todolist.service.ToDoService;



@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @MockBean
    private ToDoService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
    	
    	ItemDto newItem = ItemDto.builder()
                .description("Complete assignment")
                .dueDate("2023-01-08T18:50")
                .status("NotDone")
                .build();
    	
        when(service.addItem(any(ItemDto.class))).thenReturn(newItem);

    	mockMvc.perform( MockMvcRequestBuilders
    		      .post("/todo/add")
    		      .content(asJsonString(newItem))
    		      .contentType(MediaType.APPLICATION_JSON)
    		      .accept(MediaType.APPLICATION_JSON))
    	      .andExpect(status().isCreated())
    	      .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Complete assignment"));
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }     
}
