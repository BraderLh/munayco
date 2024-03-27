package com.pizzeria.munayco.service;

import com.pizzeria.munayco.aggregates.request.RequestMenu;
import com.pizzeria.munayco.aggregates.response.ResponseBase;

public interface MenuService {
    ResponseBase createMenu(RequestMenu requestMenu);
    ResponseBase deleteMenuById(Integer id);
    ResponseBase findAllMenus();
    ResponseBase findMenuById(Integer id);
    ResponseBase updateMenuById(Integer id, RequestMenu requestMenu);
}
