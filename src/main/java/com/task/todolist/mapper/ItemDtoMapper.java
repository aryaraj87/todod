package com.task.todolist.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.task.todolist.enumeration.ItemStatus;
import com.task.todolist.model.Item;
import com.task.todolist.model.dto.ItemDto;

@Component
public class ItemDtoMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	public List<ItemDto> convertToDtoList(List<Item> itemList){
		return itemList.stream().map(this::convertToDto).collect(Collectors.toList());
	    }
	 
	 public ItemDto convertToDto(Item item) {
		 return modelMapper.map(item, ItemDto.class);
	 } 
	 
	 public Item convertDtoToEnitity(ItemDto itemDto) {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		 LocalDateTime dateTime = LocalDateTime.parse(itemDto.getDueDate(), formatter);
		 Item item = new Item();
		 item.setDescription(itemDto.getDescription());
		 item.setStatus(ItemStatus.valueOf(itemDto.getStatus()));
		 item.setDueDate(dateTime);
		 return item;
	 } 
}
