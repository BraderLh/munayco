package com.pizzeria.munayco.repository;

import com.pizzeria.munayco.entity.ItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<ItemsEntity, Integer> {
}
