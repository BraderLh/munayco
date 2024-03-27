package com.pizzeria.munayco.controller;

import com.pizzeria.munayco.aggregates.request.RequestItem;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.service.ItemsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/items")
public class ItemsController {
    private final ItemsService itemsService;

    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @PostMapping
    public ResponseBase createItem(@RequestBody RequestItem requestItem) {
        return itemsService.createItem(requestItem);
    }

    @DeleteMapping("{id}")
    public ResponseBase deleteItem(@PathVariable int id) {
        return itemsService.deleteItemById(id);
    }

    @GetMapping("{id}")
    public ResponseBase findOneItem(@PathVariable int id) {
        return itemsService.deleteItemById(id);
    }

    @GetMapping()
    public ResponseBase findAllItems() {
        return itemsService.findAllItems();
    }

    @PatchMapping("{id}")
    public ResponseBase updateItem(@PathVariable int id, @RequestBody RequestItem requestItem) {
        return itemsService.updateItemById(id, requestItem);
    }
}
