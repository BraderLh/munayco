package com.pizzeria.munayco.service.impl;

import com.pizzeria.munayco.aggregates.constants.Constants;
import com.pizzeria.munayco.aggregates.request.RequestUser;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.entity.UsersEntity;
import com.pizzeria.munayco.repository.UsersRepository;
import com.pizzeria.munayco.security.PersonDetailService;
import com.pizzeria.munayco.security.jwt.JwtUtil;
import com.pizzeria.munayco.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final PersonDetailService personDetailService;
    private final JwtUtil jwtUtil;

    public UsersServiceImpl(UsersRepository usersRepository, AuthenticationManager authenticationManager, PersonDetailService personDetailService, JwtUtil jwtUtil) {
        this.usersRepository = usersRepository;
        this.authenticationManager = authenticationManager;
        this.personDetailService = personDetailService;
        this.jwtUtil = jwtUtil;
    }

    private UsersEntity getUsersFromRequest(RequestUser requestUser) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setEmail(requestUser.getEmail());
        usersEntity.setPassword(requestUser.getPassword());
        usersEntity.setNames(requestUser.getNames());
        usersEntity.setSurnames(requestUser.getSurnames());
        usersEntity.setStatus(Constants.STATUS_ACTIVE);
        usersEntity.setRole(Constants.AUDIT_USER);
        usersEntity.setUserCreate(Constants.AUDIT_ADMIN);
        usersEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        return usersEntity;
    }

    @Override
    public ResponseBase signUp(RequestUser requestUser) {
        log.info("Iniciando sesión con el usuario: " + requestUser.toString());
        UsersEntity usersEntity = getUsersFromRequest(requestUser);
        usersRepository.save(usersEntity);
        log.info("Finalizando inicio de sesión");
        return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(usersEntity));
    }

    @Override
    public ResponseBase login(Map<String, String> requestMap) {
        log.info("Init login");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),
                            requestMap.get("password")));
            if (authentication.isAuthenticated()) {
                if (personDetailService.getUsers().getStatus() == Constants.STATUS_ACTIVE) {
                    String token = jwtUtil.generateToken(personDetailService.getUsers().getEmail(),
                            personDetailService.getUsers().getRole());
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(token));
                }
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_LOGIN, Optional.empty());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR, Optional.of("Invalid or incorrect password"));
    }

    @Override
    public ResponseBase getAllUsers() {
        return null;
    }

    @Override
    public ResponseBase getUser(String email) {
        return null;
    }

    @Override
    public ResponseBase deleteUser(int id) {
        return null;
    }
}
