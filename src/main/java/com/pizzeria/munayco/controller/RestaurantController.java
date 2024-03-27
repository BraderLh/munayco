package com.pizzeria.munayco.controller;

import com.pizzeria.munayco.aggregates.request.RequestRestaurant;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.service.RestaurantService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/create")
    public ResponseBase createRestaurant(@RequestBody RequestRestaurant requestRestaurant) {
        return restaurantService.createRestaurant(requestRestaurant);
    }

    @DeleteMapping("{id}")
    public ResponseBase deleteRestaurant(@PathVariable int id) {
        return restaurantService.deleteRestaurantById(id);
    }

    @GetMapping("/all")
    public ResponseBase findAllRestaurants() {
        return restaurantService.findAllRestaurants();
    }

    @GetMapping("{id}")
    public ResponseBase findOneRestaurant(@PathVariable int id) {
        return restaurantService.findRestaurantById(id);
    }

    @PatchMapping("{id}")
    public ResponseBase updateRestaurant(@PathVariable int id, @RequestBody RequestRestaurant requestRestaurant) {
        return restaurantService.updateRestaurantById(id, requestRestaurant);
    }
}