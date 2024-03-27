package com.pizzeria.munayco.repository;

import com.pizzeria.munayco.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {
    UsersEntity findByEmail(@Param("email") String email);
}
