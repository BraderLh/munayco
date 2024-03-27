package com.pizzeria.munayco.service;

import com.pizzeria.munayco.aggregates.request.RequestBranchOffice;
import com.pizzeria.munayco.aggregates.response.ResponseBase;

public interface BranchOfficesService {
    ResponseBase createBranchOffice(RequestBranchOffice requestBranchOffice);
    ResponseBase deleteBranchOfficeById(Integer id);
    ResponseBase findBranchOfficeById(Integer id);
    ResponseBase findAllBranchOffices();
    ResponseBase updateBranchOfficeById(Integer id, RequestBranchOffice requestBranchOffice);
}
