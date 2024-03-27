package com.pizzeria.munayco.util;

import com.pizzeria.munayco.aggregates.request.RequestItemType;
import org.springframework.stereotype.Component;

@Component
public class ItemsTypeValidation {
    public final GeneralValidations generalValidations;

    public ItemsTypeValidation(GeneralValidations generalValidations) {
        this.generalValidations = generalValidations;
    }

    public boolean validateInput(RequestItemType requestItemType) {
        if (requestItemType == null) {
            return false;
        }

        if (requestItemType.getPrice() < 0) {
            return false;
        }

        return generalValidations.isNullOrEmpty(requestItemType.getIngredients()) ||
                generalValidations.isNullOrEmpty(requestItemType.getSize()) ||
                generalValidations.isNullOrEmpty(String.valueOf(requestItemType.getPrice())) ||
                generalValidations.isNullOrEmpty(requestItemType.getItemType()) ||
                generalValidations.isNullOrEmpty(requestItemType.getName()) ||
                generalValidations.isNullOrEmpty(requestItemType.getImage());
    }
}
