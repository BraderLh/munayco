package com.pizzeria.munayco.repository;

import com.pizzeria.munayco.entity.PromotionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionsRepository extends JpaRepository<PromotionsEntity, Integer> {
    PromotionsEntity findFirstByOrderByPromotionIdDesc();
}
