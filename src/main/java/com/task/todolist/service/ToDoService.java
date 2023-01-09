package com.task.todolist.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.todolist.common.CommonConstants;
import com.task.todolist.enumeration.ItemStatus;
import com.task.todolist.exceptions.NotAllowed;
import com.task.todolist.exceptions.NotFoundException;
import com.task.todolist.mapper.ItemDtoMapper;
import com.task.todolist.model.Item;
import com.task.todolist.model.dto.ItemDto;
import com.task.todolist.repository.ToDoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ToDoService {

	private static Logger logger = LoggerFactory.getLogger(ToDoService.class);

	@Autowired
	private ToDoRepository toDoRepo;

	@Autowired
	private ItemDtoMapper itemDtoMapper;

	public ItemDto addItem(ItemDto itemDto) {
		logger.info(itemDto.toString());
		Item newItem = itemDtoMapper.convertDtoToEnitity(itemDto);
		newItem.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
		return itemDtoMapper.convertToDto(toDoRepo.save(newItem));

	}

	public ItemDto updateItem(ItemDto itemDto, Long id, String attributeToBechanged) {
		Optional<Item> item = toDoRepo.findById(id);
		if (item.isPresent()) {
			LocalDateTime nowTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
			
			if (!item.get().getStatus().equals(ItemStatus.PASTDUE) && item.get().getDueDate().compareTo(nowTime) > 0) {
				
				Item updateItem = item.get();
				
				if (attributeToBechanged.equalsIgnoreCase(CommonConstants.COL_DESC))
					updateItem.setDescription(itemDto.getDescription());

				else {
					if (item.get().getStatus().equals(ItemStatus.PASTDUE))
						throw new NotAllowed(ItemStatus.PASTDUE); // Not allowed to change the status to past_due
					item.get().setStatus(ItemStatus.valueOf(itemDto.getStatus()));
					if (item.get().getStatus().equals(ItemStatus.Done)) {
						updateItem.setCompletedDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
					}
				}
				return itemDtoMapper.convertToDto(toDoRepo.save(updateItem));

			} else
				throw new NotAllowed(id); // item valid or not condition

		} else
			throw new NotFoundException(id); // end of item not found condition
	}

	public void updatePastDueItemStatus() {
		try {
			LocalDateTime dueDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
			List<Item> items = toDoRepo.findAllByStatusAndDueDate(ItemStatus.NotDone.toString(),dueDate);
			items.forEach(f -> f.setStatus(ItemStatus.PASTDUE));
			toDoRepo.saveAll(items);
		}catch (Exception e) {
			logger.info(e.getMessage());
		}
	}

	public ItemDto getItemDetails(Long id) {
		ItemDto itemDto = itemDtoMapper
				.convertToDto(toDoRepo.findById(id).orElseThrow(() -> new NotFoundException(id)));
		return itemDto;
	}

	public List<ItemDto> getAllItems(ItemStatus status) {
		List<Item> items = new ArrayList<Item>();
		if (null == status)
			items = toDoRepo.findAll();
		else {
			logger.info(status.toString());
			items = toDoRepo.findAllByStatus(status);
		}
		return itemDtoMapper.convertToDtoList(items);
	}
}
