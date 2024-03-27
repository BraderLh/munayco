package com.pizzeria.munayco.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestItemType {
    private String itemType;
    private String name;
    private String image;
    private String size;
    private String ingredients;
    private double price;
}
