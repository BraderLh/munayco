package com.pizzeria.munayco.entity;

import com.pizzeria.munayco.entity.common.Audit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "items_type")
public class ItemsTypeEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_type_id")
    private Integer itemsTypeId;

    @Column(name = "code_type", length = 8, nullable = false, unique = true)
    private String code;

    @Column(name = "item_type", length = 8, nullable = false)
    private String itemType;

    @Column(name = "image_url", nullable = false)
    private String image;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "size", length = 100, nullable = false)
    private String size;

    @Column(name = "ingredients", length = 200, nullable = false)
    private String ingredients;

    @Column(name = "price", nullable = false)
    private Double price;
}
