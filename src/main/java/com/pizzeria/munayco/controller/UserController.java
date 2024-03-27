package com.pizzeria.munayco.controller;

import com.pizzeria.munayco.aggregates.request.RequestUser;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.service.UsersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/auth")
public class UserController {
    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/singUp")
    public ResponseBase singUp(@RequestBody RequestUser requestUser) {
        return usersService.signUp(requestUser);
    }

    @PostMapping("/login")
    public ResponseBase login(@RequestBody Map<String,String> requestMap){
        return usersService.login(requestMap);
    }
}
