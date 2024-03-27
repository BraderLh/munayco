package com.pizzeria.munayco.service;

import com.pizzeria.munayco.aggregates.request.RequestUser;
import com.pizzeria.munayco.aggregates.response.ResponseBase;

import java.util.Map;

public interface UsersService {
    ResponseBase signUp(RequestUser requestUser);
    ResponseBase login(Map<String, String> requestMap);
    ResponseBase getAllUsers();
    ResponseBase getUser(String email);
    ResponseBase deleteUser(int id);
}
