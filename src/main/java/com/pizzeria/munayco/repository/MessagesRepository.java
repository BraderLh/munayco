package com.pizzeria.munayco.repository;

import com.pizzeria.munayco.entity.MessagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<MessagesEntity, Integer> {
}
