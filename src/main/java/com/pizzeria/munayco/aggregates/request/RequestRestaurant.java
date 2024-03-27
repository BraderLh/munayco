package com.pizzeria.munayco.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestRestaurant {
    private String name;
    private String ruc;
    private String socialMedia;
    private String description;
    private int menuEntityId;
}
