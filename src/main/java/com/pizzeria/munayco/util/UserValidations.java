package com.pizzeria.munayco.util;

import com.pizzeria.munayco.aggregates.request.RequestUser;
import org.springframework.stereotype.Component;

@Component
public class UserValidations {
    public final GeneralValidations generalValidations;

    public UserValidations(GeneralValidations generalValidations) {
        this.generalValidations = generalValidations;
    }

    public boolean validateInput(RequestUser requestUser) {
        if (requestUser == null) {
            return false;
        }

        return generalValidations.isNullOrEmpty(requestUser.getEmail()) ||
                generalValidations.isNullOrEmpty(requestUser.getPassword());
    }

}
