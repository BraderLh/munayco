package com.pizzeria.munayco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pizzeria.munayco.entity.common.Audit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "restaurant")
public class RestaurantEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ruc", length = 11, nullable = false, unique = true)
    private String ruc;

    @Column(name = "social_media")
    private String socialMedia;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_menu", referencedColumnName = "menu_id")
    private MenuEntity menuEntity;
}
