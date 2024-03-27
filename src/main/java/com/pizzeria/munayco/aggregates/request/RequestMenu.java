package com.pizzeria.munayco.aggregates.request;

import com.pizzeria.munayco.entity.ItemsEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RequestMenu {
    private String name;
    private String menuFile;
    private Set<ItemsEntity> itemsEntitySet = new HashSet<>();
}
