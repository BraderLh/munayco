package com.pizzeria.munayco.service.impl;

import com.pizzeria.munayco.aggregates.constants.Constants;
import com.pizzeria.munayco.aggregates.request.RequestMessage;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.entity.MessagesEntity;
import com.pizzeria.munayco.entity.RestaurantEntity;
import com.pizzeria.munayco.repository.MessagesRepository;
import com.pizzeria.munayco.repository.RestaurantRepository;
import com.pizzeria.munayco.service.MessagesService;
import com.pizzeria.munayco.util.MessageValidations;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class MessagesServiceImpl implements MessagesService {
    private final MessagesRepository messagesRepository;
    private final MessageValidations messageValidations;
    private final RestaurantRepository restaurantRepository;

    public MessagesServiceImpl(MessagesRepository messagesRepository, MessageValidations messageValidations, RestaurantRepository restaurantRepository) {
        this.messagesRepository = messagesRepository;
        this.messageValidations = messageValidations;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public ResponseBase createMessage(RequestMessage requestMessage) {
        boolean validateInput = messageValidations.validateInput(requestMessage);
        if (validateInput) {
            MessagesEntity messagesEntity = getEntityCreate(requestMessage);
            messagesRepository.save(messagesEntity);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(messagesEntity));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    private MessagesEntity getEntityCreate(RequestMessage requestMessage) {
        MessagesEntity messagesEntity = new MessagesEntity();
        messagesEntity.setEmail(requestMessage.getEmail());
        messagesEntity.setDescription(requestMessage.getDescription());
        messagesEntity.setSubject(requestMessage.getSubject());
        messagesEntity.setRestaurantEntity(getRestaurant(requestMessage.getRestaurantEntityId()));
        messagesEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        messagesEntity.setUserCreate(Constants.AUDIT_ADMIN);
        messagesEntity.setStatus(Constants.STATUS_ACTIVE);
        return messagesEntity;
    }

    private RestaurantEntity getRestaurant(int restaurantEntityId) {
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(restaurantEntityId);
        return restaurantEntity.orElse(null);
    }

    @Override
    public ResponseBase deleteMessageById(Integer id) {
        if (messagesRepository.existsById(id)) {
            Optional<MessagesEntity> messagesEntity = messagesRepository.findById(id);
            if (messagesEntity.isPresent()) {
                MessagesEntity messagesDeleted = getEntityDelete(messagesEntity.get());
                messagesRepository.save(messagesDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(messagesDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }

        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' +  Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    private MessagesEntity getEntityDelete(MessagesEntity messagesEntity) {
        messagesEntity.setStatus(Constants.STATUS_INACTIVE);
        messagesEntity.setUserDelete(Constants.AUDIT_ADMIN);
        messagesEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        return messagesEntity;
    }

    @Override
    public ResponseBase findMessageById(Integer id) {
        if (messagesRepository.existsById(id)) {
            Optional<MessagesEntity> messagesEntity = messagesRepository.findById(id);
            if (messagesEntity.isPresent()) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(messagesEntity));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findAllMessages() {
        List<MessagesEntity> messagesEntityList = messagesRepository.findAll();
        if (!messagesEntityList.isEmpty()) {
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(messagesEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    @Override
    public ResponseBase updateMessageById(Integer id, RequestMessage requestMessage) {
        if (messagesRepository.existsById(id)) {
            Optional<MessagesEntity> messagesEntity = messagesRepository.findById(id);
            if (messagesEntity.isPresent()) {
                boolean validateInput = messageValidations.validateInput(requestMessage);
                if (validateInput) {
                    MessagesEntity messageUpdated = getEntityUpdate(messagesEntity.get(), requestMessage);
                    messagesRepository.save(messageUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(messageUpdated));
                } else {
                    return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
                }
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ERROR_NOT_UPDATE, Optional.empty());
            }

        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    private MessagesEntity getEntityUpdate(MessagesEntity messagesEntity, RequestMessage requestMessage) {
        messagesEntity.setEmail(requestMessage.getEmail());
        messagesEntity.setDescription(requestMessage.getDescription());
        messagesEntity.setSubject(requestMessage.getSubject());
        messagesEntity.setRestaurantEntity(getRestaurant(requestMessage.getRestaurantEntityId()));
        messagesEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        messagesEntity.setUserModify(Constants.AUDIT_ADMIN);
        return messagesEntity;
    }
}
