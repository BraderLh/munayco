package com.pizzeria.munayco.service;

import com.pizzeria.munayco.aggregates.request.RequestRestaurant;
import com.pizzeria.munayco.aggregates.response.ResponseBase;

public interface RestaurantService {
    ResponseBase createRestaurant(RequestRestaurant requestRestaurant);
    ResponseBase deleteRestaurantById(Integer id);
    ResponseBase findRestaurantById(Integer id);
    ResponseBase findAllRestaurants();
    ResponseBase updateRestaurantById(Integer id, RequestRestaurant requestRestaurant);
}
