package com.pizzeria.munayco.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPromotion {
    private String description;
    private String name;
    private int value;
}
