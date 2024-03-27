package com.pizzeria.munayco.controller;

import com.pizzeria.munayco.aggregates.request.RequestItemType;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.service.ItemsTypeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/itemType")
public class ItemsTypeController {
    private final ItemsTypeService itemsTypeService;

    public ItemsTypeController(ItemsTypeService itemsTypeService) {
        this.itemsTypeService = itemsTypeService;
    }

    @PostMapping
    public ResponseBase createItemType(@RequestBody RequestItemType requestItemType) {
        return itemsTypeService.createItemTypeService(requestItemType);
    }

    @DeleteMapping("{id}")
    public ResponseBase deleteItemType(@PathVariable int id) {
        return itemsTypeService.deleteItemTypeServiceById(id);
    }

    @GetMapping()
    public ResponseBase findAllItemsType() {
        return itemsTypeService.findAllItemsType();
    }

    @GetMapping("{id}")
    public ResponseBase findOneItemType(@PathVariable int id) {
        return itemsTypeService.findItemTypeServiceById(id);
    }

    @PatchMapping("{id}")
    public ResponseBase updateItemType(@PathVariable int id, @RequestBody RequestItemType requestItemType) {
        return itemsTypeService.updateItemTypeService(id, requestItemType);
    }
}
