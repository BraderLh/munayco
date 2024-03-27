package com.pizzeria.munayco.service;

import com.pizzeria.munayco.aggregates.request.RequestItemType;
import com.pizzeria.munayco.aggregates.response.ResponseBase;

public interface ItemsTypeService {
    ResponseBase createItemTypeService(RequestItemType requestItemType);
    ResponseBase deleteItemTypeServiceById(Integer id);
    ResponseBase findItemTypeServiceById(Integer id);
    ResponseBase findAllItemsType();
    ResponseBase updateItemTypeService(Integer id, RequestItemType requestItemType);
}
