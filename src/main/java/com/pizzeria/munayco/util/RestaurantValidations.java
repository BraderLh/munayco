package com.pizzeria.munayco.util;

import com.pizzeria.munayco.aggregates.request.RequestRestaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantValidations {
    public final GeneralValidations generalValidations;

    public RestaurantValidations(GeneralValidations generalValidations) {
        this.generalValidations = generalValidations;
    }

    public boolean validateInput(RequestRestaurant requestRestaurant) {
        if (requestRestaurant == null) {
            return false;
        }

        return generalValidations.isNullOrEmpty(requestRestaurant.getDescription()) ||
                generalValidations.isNullOrEmpty(requestRestaurant.getRuc()) ||
                generalValidations.isNullOrEmpty(requestRestaurant.getName()) ||
                generalValidations.isNullOrEmpty(requestRestaurant.getSocialMedia()) ||
                generalValidations.isNullOrEmpty(String.valueOf(requestRestaurant.getMenuEntityId()));
    }
}
