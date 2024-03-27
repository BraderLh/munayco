package com.pizzeria.munayco.util;

import com.pizzeria.munayco.aggregates.request.RequestMessage;
import org.springframework.stereotype.Component;

@Component
public class MessageValidations {
    public final GeneralValidations generalValidations;

    public MessageValidations(GeneralValidations generalValidations) {
        this.generalValidations = generalValidations;
    }

    public boolean validateInput(RequestMessage requestMessage) {
        if (requestMessage == null) {
            return false;
        }

        return generalValidations.isNullOrEmpty(requestMessage.getDescription()) ||
                generalValidations.isNullOrEmpty(requestMessage.getEmail()) ||
                generalValidations.isNullOrEmpty(requestMessage.getSubject()) ||
                generalValidations.isNullOrEmpty(String.valueOf(requestMessage.getRestaurantEntityId()));
    }
}
