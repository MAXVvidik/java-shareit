package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryItemStorage implements ItemStorage {

    private final Map<Integer, Item> items = new HashMap<>();
    private int id = 0;

    @Override
    public List<Item> getAllItems() {
        return new ArrayList<>(items.values());
    }


    @Override
    public boolean isContainItem(int id) {
        return items.containsKey(id);
    }

    private int getId() {
        id++;
        return id;
    }
}
