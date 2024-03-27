package com.pizzeria.munayco.util;

import org.springframework.stereotype.Component;

@Component
public class GeneralValidations {
    public boolean isNullOrEmpty(String data){
        return data != null && !data.isEmpty() && !data.isBlank();
    }
}
