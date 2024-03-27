package com.pizzeria.munayco.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUser {
    private String email;
    private String password;
    private String names;
    private String surnames;
}
