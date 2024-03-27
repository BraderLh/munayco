package com.pizzeria.munayco.service;

import com.pizzeria.munayco.aggregates.request.RequestItem;
import com.pizzeria.munayco.aggregates.response.ResponseBase;

public interface ItemsService {
    ResponseBase createItem(RequestItem requestItem);
    ResponseBase deleteItemById(Integer id);
    ResponseBase findItemById(Integer id);
    ResponseBase findAllItems();
    ResponseBase updateItemById(Integer id, RequestItem requestItem);
}
