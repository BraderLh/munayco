package com.pizzeria.munayco.entity;

import com.pizzeria.munayco.entity.common.Audit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "items")
public class ItemsEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @ManyToOne
    @JoinColumn(name = "id_item_type", nullable = false)
    private ItemsTypeEntity itemsTypeEntity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "items_promotions",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "promotion_id")
    )
    private Set<PromotionsEntity> promotionsEntitySet = new HashSet<>();
}
