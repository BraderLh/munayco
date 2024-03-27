package com.pizzeria.munayco.util;

import com.pizzeria.munayco.aggregates.request.RequestBranchOffice;
import org.springframework.stereotype.Component;

@Component
public class BranchOfficeValidations {
    public final GeneralValidations generalValidations;

    public BranchOfficeValidations(GeneralValidations generalValidations) {
        this.generalValidations = generalValidations;
    }

    public boolean validateInput(RequestBranchOffice requestBranchOffice) {
        if (requestBranchOffice == null) {
            return false;
        }

        return generalValidations.isNullOrEmpty(requestBranchOffice.getAddress()) ||
                generalValidations.isNullOrEmpty(requestBranchOffice.getLocation()) ||
                generalValidations.isNullOrEmpty(requestBranchOffice.getPhone()) ||
                generalValidations.isNullOrEmpty(requestBranchOffice.getTelephone()) ||
                generalValidations.isNullOrEmpty(requestBranchOffice.getOpeningHours());
    }
}
