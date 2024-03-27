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
@Table(name = "promotions")
public class PromotionsEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Integer promotionId;

    @Column(name = "description")
    private String description;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "promo_code", length = 8, nullable = false, unique = true)
    private String promoCode;

    @Column(name = "value", nullable = false)
    private Integer value;
}
