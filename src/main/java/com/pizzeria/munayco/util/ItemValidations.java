package com.pizzeria.munayco.util;

import com.pizzeria.munayco.aggregates.request.RequestItem;
import org.springframework.stereotype.Component;

@Component
public class ItemValidations {
    public final GeneralValidations generalValidations;

    public ItemValidations(GeneralValidations generalValidations) {
        this.generalValidations = generalValidations;
    }

    public boolean validateInput(RequestItem requestItem) {
        if (requestItem == null) {
            return false;
        }

        return generalValidations.isNullOrEmpty(String.valueOf(requestItem.getItemsTypeEntityId()));
    }
}
