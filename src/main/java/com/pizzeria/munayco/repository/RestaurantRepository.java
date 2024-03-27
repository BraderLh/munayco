package com.pizzeria.munayco.repository;

import com.pizzeria.munayco.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {
}
