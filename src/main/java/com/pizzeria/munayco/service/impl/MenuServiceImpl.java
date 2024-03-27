package com.pizzeria.munayco.service.impl;

import com.pizzeria.munayco.aggregates.constants.Constants;
import com.pizzeria.munayco.aggregates.request.RequestMenu;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.entity.MenuEntity;
import com.pizzeria.munayco.repository.MenuRepository;
import com.pizzeria.munayco.service.MenuService;
import com.pizzeria.munayco.util.MenuValidations;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuValidations menuValidations;

    public MenuServiceImpl(MenuRepository menuRepository, MenuValidations menuValidations) {
        this.menuRepository = menuRepository;
        this.menuValidations = menuValidations;
    }

    @Override
    public ResponseBase createMenu(RequestMenu requestMenu) {
        boolean validateInput = menuValidations.validateInput(requestMenu);
        if (validateInput) {
            MenuEntity menuEntityCreated = getEntityCreate(requestMenu);
            menuRepository.save(menuEntityCreated);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(menuEntityCreated));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                    + Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    private MenuEntity getEntityCreate(RequestMenu requestMenu) {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setName(requestMenu.getName());
        menuEntity.setMenuFile(requestMenu.getMenuFile());
        menuEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        menuEntity.setUserCreate(Constants.AUDIT_ADMIN);
        menuEntity.setStatus(Constants.STATUS_ACTIVE);
        return menuEntity;
    }

    @Override
    public ResponseBase deleteMenuById(Integer id) {
        if (menuRepository.existsById(id)) {
            Optional<MenuEntity> menuEntity = menuRepository.findById(id);
            if (menuEntity.isPresent()) {
                MenuEntity menuEntityDeleted = getEntityDelete(menuEntity.get());
                menuRepository.save(menuEntityDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(menuEntityDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                        + Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }

        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                    + Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    private MenuEntity getEntityDelete(MenuEntity menuEntity) {
        menuEntity.setStatus(Constants.STATUS_INACTIVE);
        menuEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        menuEntity.setUserModify(Constants.AUDIT_ADMIN);
        return menuEntity;
    }

    @Override
    public ResponseBase findAllMenus() {
        List<MenuEntity> menuEntityList = menuRepository.findAll();
        if (!menuEntityList.isEmpty()) {
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(menuEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                    + Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    @Override
    public ResponseBase findMenuById(Integer id) {
        if (menuRepository.existsById(id)) {
            Optional<MenuEntity> menuEntity = menuRepository.findById(id);
            if (menuEntity.isPresent()) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(menuEntity));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                        + Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                    + Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase updateMenuById(Integer id, RequestMenu requestMenu) {
        if (menuRepository.existsById(id)) {
            Optional<MenuEntity> menuEntity = menuRepository.findById(id);
            if (menuEntity.isPresent()) {
                boolean validateInput = menuValidations.validateInput(requestMenu);
                if (validateInput) {
                    MenuEntity menuEntityUpdated = getEntityUpdate(menuEntity.get(), requestMenu);
                    menuRepository.save(menuEntityUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(menuEntityUpdated));
                } else {
                    return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                            + Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
                }
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                        + Constants.MESS_ERROR_NOT_UPDATE, Optional.empty());
            }

        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                    + Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    private MenuEntity getEntityUpdate(MenuEntity menuEntity, RequestMenu requestMenu) {
        menuEntity.setName(requestMenu.getName());
        menuEntity.setMenuFile(requestMenu.getMenuFile());
        menuEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        menuEntity.setUserModify(Constants.AUDIT_ADMIN);
        return menuEntity;
    }
}
