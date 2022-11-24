package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.InputDataException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.item.validate.ValidateItemData;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemService {


    private final ItemStorage itemStorage;
    private final UserService userService;
    private final ValidateItemData validateItemData;

    @Autowired
    public ItemService(ItemStorage itemStorage, UserService userService, ValidateItemData validateItemData) {
        this.itemStorage = itemStorage;
        this.userService = userService;
        this.validateItemData = validateItemData;
    }


    public ItemDto addItem(ItemDto itemDto, Integer userId) {
        Item item = ItemMapper.fromItemDto(itemDto);
        item.setUserId(userId);
        if (userId == null) {
            throw new ValidationException("Отсутствует id пользователя, создавший данную вещь");
        }
        if (!userService.isContainsUser(userId)) {
            throw new InputDataException("Пользователь с id=" + userId + " не найден в БД");
        }
        if (validateItemData.checkAllData(item)) {
            item.setId(userId);
            return ItemMapper.toItemDto(itemStorage.addItem(item));
        } else {
            throw new ValidationException("Ошибка во входных данных");
        }
    }

    public ItemDto getItemById(int id) {
        if (isContainItem(id)) {
            return ItemMapper.toItemDto(itemStorage.getItemById(id));
        } else {
            throw new InputDataException("Вещь по id не найдена");
        }
    }

    public List<ItemDto> getItemsByUserId(int userId) {
        return itemStorage.getItemsByUserId(userId)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public List<ItemDto> getItemsBySubString(String text) {
        return itemStorage.getItemsBySubString(text)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public List<ItemDto> getAllItems(Integer userId) {
        if (userId != null) {
            return getItemsByUserId(userId);
        } else {
            return itemStorage.getAllItems()
                    .stream()
                    .map(ItemMapper::toItemDto)
                    .collect(Collectors.toList());
        }
    }

    public ItemDto updateItem(ItemDto itemDto, Integer userId) {
        if (userId == null) {
            throw new ValidationException("Отсутствует id пользователя, создавший данную вещь");
        }
        Item itemFromDb = itemStorage.getItemById(itemDto.getId());
        if (itemFromDb.getUserId() == userId) {
            Item item = ItemMapper.fromItemDto(itemDto);
            return ItemMapper.toItemDto(itemStorage.updateItem(item));
        } else {
            throw new InputDataException("Id пользователя не совпадает с id создавшего вещь пользователя");
        }
    }

    public void deleteItem(int id) {
        itemStorage.deleteItem(id);
    }

    public boolean isContainItem(int id) {
        return itemStorage.isContainItem(id);
    }
}