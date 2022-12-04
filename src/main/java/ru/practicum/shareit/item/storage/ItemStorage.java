package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;


import java.util.List;

public interface ItemStorage {

    List<Item> getAllItems();

    boolean isContainItem(int id);
}