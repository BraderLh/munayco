package com.pizzeria.munayco.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestMessage {
    private String email;
    private String subject;
    private String description;
    private int restaurantEntityId;
}
