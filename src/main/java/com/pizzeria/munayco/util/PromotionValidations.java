package com.pizzeria.munayco.util;

import com.pizzeria.munayco.aggregates.request.RequestPromotion;
import org.springframework.stereotype.Component;

@Component
public class PromotionValidations {
    public final GeneralValidations generalValidations;

    public PromotionValidations(GeneralValidations generalValidations) {
        this.generalValidations = generalValidations;
    }

    public boolean validateInput(RequestPromotion requestPromotion) {
        if (requestPromotion == null) {
            return false;
        }

        if (requestPromotion.getValue() < 0) {
            return false;
        }

        return generalValidations.isNullOrEmpty(requestPromotion.getDescription()) ||
                generalValidations.isNullOrEmpty(requestPromotion.getName()) ||
                generalValidations.isNullOrEmpty(String.valueOf(requestPromotion.getValue()));
    }
}
