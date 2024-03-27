package com.pizzeria.munayco.repository;

import com.pizzeria.munayco.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {
}
