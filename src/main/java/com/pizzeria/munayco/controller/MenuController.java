package com.pizzeria.munayco.controller;

import com.pizzeria.munayco.aggregates.request.RequestMenu;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.service.MenuService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/menu")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseBase createMenu(@RequestBody RequestMenu requestMenu) {
        return menuService.createMenu(requestMenu);
    }

    @DeleteMapping("{id}")
    public ResponseBase deleteMenu(@PathVariable int id) {
        return menuService.deleteMenuById(id);
    }

    @GetMapping
    public ResponseBase findAllMenus() {
        return menuService.findAllMenus();
    }

    @GetMapping("{id}")
    public ResponseBase findOneMenu(@PathVariable int id) {
        return menuService.findMenuById(id);
    }

    @PatchMapping("{id}")
    public ResponseBase updateMenu(@PathVariable int id, @RequestBody RequestMenu requestMenu) {
        return menuService.updateMenuById(id, requestMenu);
    }
}
