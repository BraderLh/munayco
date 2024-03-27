package com.pizzeria.munayco.util;

import com.pizzeria.munayco.aggregates.request.RequestMenu;
import org.springframework.stereotype.Component;

@Component
public class MenuValidations {
    public final GeneralValidations generalValidations;

    public MenuValidations(GeneralValidations generalValidations) {
        this.generalValidations = generalValidations;
    }

    public boolean validateInput(RequestMenu requestMenu) {
        if (requestMenu == null) {
            return false;
        }

        return generalValidations.isNullOrEmpty(requestMenu.getName()) ||
                generalValidations.isNullOrEmpty(requestMenu.getMenuFile());
    }
}
