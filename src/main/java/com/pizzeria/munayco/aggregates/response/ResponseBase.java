package com.pizzeria.munayco.aggregates.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseBase {
    private int code;
    private String message;
    private Optional data;
}
