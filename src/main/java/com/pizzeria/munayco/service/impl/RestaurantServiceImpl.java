package com.pizzeria.munayco.service.impl;

import com.pizzeria.munayco.aggregates.constants.Constants;
import com.pizzeria.munayco.aggregates.request.RequestRestaurant;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.entity.MenuEntity;
import com.pizzeria.munayco.entity.RestaurantEntity;
import com.pizzeria.munayco.repository.MenuRepository;
import com.pizzeria.munayco.repository.RestaurantRepository;
import com.pizzeria.munayco.service.RestaurantService;
import com.pizzeria.munayco.util.RestaurantValidations;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantValidations restaurantValidations;
    private final MenuRepository menuRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantValidations restaurantValidations, MenuRepository menuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantValidations = restaurantValidations;
        this.menuRepository = menuRepository;
    }

    @Override
    public ResponseBase createRestaurant(RequestRestaurant requestRestaurant) {
        boolean validateInput = restaurantValidations.validateInput(requestRestaurant);
        if (validateInput) {
            RestaurantEntity restaurantCreated = getEntityCreate(requestRestaurant);
            restaurantRepository.save(restaurantCreated);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(restaurantCreated));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                    + Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    private RestaurantEntity getEntityCreate(RequestRestaurant requestRestaurant) {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setName(requestRestaurant.getName());
        restaurantEntity.setRuc(requestRestaurant.getRuc());
        restaurantEntity.setDescription(requestRestaurant.getDescription());
        restaurantEntity.setSocialMedia(requestRestaurant.getSocialMedia());
        restaurantEntity.setMenuEntity(getMenu(requestRestaurant.getMenuEntityId()));
        restaurantEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        restaurantEntity.setUserCreate(Constants.AUDIT_ADMIN);
        restaurantEntity.setStatus(Constants.STATUS_ACTIVE);
        return restaurantEntity;
    }

    private MenuEntity getMenu(int menuEntityId) {
        Optional<MenuEntity> menuEntity = menuRepository.findById(menuEntityId);
        return menuEntity.orElse(null);
    }

    @Override
    public ResponseBase deleteRestaurantById(Integer id) {
        if (restaurantRepository.existsById(id)) {
            Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
            if (restaurantEntity.isPresent()) {
                RestaurantEntity restaurantDeleted = getEntityDelete(restaurantEntity.get());
                restaurantRepository.save(restaurantDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(restaurantDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                        + Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }

        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                    +  Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    private RestaurantEntity getEntityDelete(RestaurantEntity restaurantEntity) {
        restaurantEntity.setStatus(Constants.STATUS_INACTIVE);
        restaurantEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        restaurantEntity.setUserDelete(Constants.AUDIT_ADMIN);
        return restaurantEntity;
    }

    @Override
    public ResponseBase findRestaurantById(Integer id) {
        if (restaurantRepository.existsById(id)) {
            Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
            if (restaurantEntity.isPresent()) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(restaurantEntity));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                        + Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' +
                    Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findAllRestaurants() {
        List<RestaurantEntity> restaurantEntityList = restaurantRepository.findAll();
        if (!restaurantEntityList.isEmpty()) {
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(restaurantEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' '
                    + Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    @Override
    public ResponseBase updateRestaurantById(Integer id, RequestRestaurant requestRestaurant) {
        if (restaurantRepository.existsById(id)) {
            Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
            if (restaurantEntity.isPresent()) {
                boolean validateInput = restaurantValidations.validateInput(requestRestaurant);
                if (validateInput) {
                    RestaurantEntity restaurantUpdated = getEntityUpdate(restaurantEntity.get(), requestRestaurant);
                    restaurantRepository.save(restaurantUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(restaurantUpdated));
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

    private RestaurantEntity getEntityUpdate(RestaurantEntity restaurantEntity, RequestRestaurant requestRestaurant) {
        restaurantEntity.setName(requestRestaurant.getName());
        restaurantEntity.setRuc(requestRestaurant.getRuc());
        restaurantEntity.setSocialMedia(requestRestaurant.getSocialMedia());
        restaurantEntity.setDescription(requestRestaurant.getDescription());
        restaurantEntity.setMenuEntity(getMenu(requestRestaurant.getMenuEntityId()));
        restaurantEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        restaurantEntity.setUserModify(Constants.AUDIT_ADMIN);
        return restaurantEntity;
    }
}
