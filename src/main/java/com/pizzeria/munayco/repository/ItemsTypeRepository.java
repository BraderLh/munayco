package com.pizzeria.munayco.repository;

import com.pizzeria.munayco.entity.ItemsTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsTypeRepository extends JpaRepository<ItemsTypeEntity, Integer> {
    ItemsTypeEntity findFirstByOrderByItemsTypeIdDesc();
}
