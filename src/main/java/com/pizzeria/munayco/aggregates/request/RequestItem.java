package com.pizzeria.munayco.aggregates.request;

import com.pizzeria.munayco.entity.PromotionsEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RequestItem {
    private int itemsTypeEntityId;
    private Set<PromotionsEntity> promotionsEntitySet = new HashSet<>();
}
