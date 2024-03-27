package com.pizzeria.munayco.service.impl;

import com.pizzeria.munayco.aggregates.constants.Constants;
import com.pizzeria.munayco.aggregates.request.RequestItemType;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.entity.ItemsTypeEntity;
import com.pizzeria.munayco.repository.ItemsTypeRepository;
import com.pizzeria.munayco.service.ItemsTypeService;
import com.pizzeria.munayco.util.ItemsTypeValidation;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ItemsTypeServiceImpl implements ItemsTypeService {
    private final ItemsTypeRepository itemsTypeRepository;
    private final ItemsTypeValidation itemsTypeValidation;

    public ItemsTypeServiceImpl(ItemsTypeRepository itemsTypeRepository, ItemsTypeValidation itemsTypeValidation) {
        this.itemsTypeRepository = itemsTypeRepository;
        this.itemsTypeValidation = itemsTypeValidation;
    }

    @Override
    public ResponseBase createItemTypeService(RequestItemType requestItemType) {
        boolean validateInput = itemsTypeValidation.validateInput(requestItemType);
        if (validateInput) {
            ItemsTypeEntity itemsTypeEntity = getEntityCreate(requestItemType);
            itemsTypeRepository.save(itemsTypeEntity);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(itemsTypeEntity));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    private ItemsTypeEntity getEntityCreate(RequestItemType requestItemType) {
        ItemsTypeEntity itemsTypeEntity = new ItemsTypeEntity();
        ItemsTypeEntity lastEntity = itemsTypeRepository.findFirstByOrderByItemsTypeIdDesc();
        if (lastEntity == null){
            itemsTypeEntity.setCode("PPM1");
        } else {
            itemsTypeEntity.setCode("PPM" + (lastEntity.getItemsTypeId()+1));
        }
        itemsTypeEntity.setItemType(requestItemType.getItemType());
        itemsTypeEntity.setName(requestItemType.getName());
        itemsTypeEntity.setImage(requestItemType.getImage());
        itemsTypeEntity.setSize(requestItemType.getSize());
        itemsTypeEntity.setIngredients(requestItemType.getIngredients());
        itemsTypeEntity.setPrice(requestItemType.getPrice());
        itemsTypeEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        itemsTypeEntity.setUserCreate(Constants.AUDIT_ADMIN);
        itemsTypeEntity.setStatus(Constants.STATUS_ACTIVE);
        return itemsTypeEntity;
    }

    @Override
    public ResponseBase deleteItemTypeServiceById(Integer id) {
        if (itemsTypeRepository.existsById(id)) {
            Optional<ItemsTypeEntity> itemsTypeEntity = itemsTypeRepository.findById(id);
            if (itemsTypeEntity.isPresent()) {
                ItemsTypeEntity itemsTypeDeleted = getEntityDelete(itemsTypeEntity.get());
                itemsTypeRepository.save(itemsTypeDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(itemsTypeDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }

        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' +  Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    private ItemsTypeEntity getEntityDelete(ItemsTypeEntity itemsTypeEntity) {
        itemsTypeEntity.setStatus(Constants.STATUS_INACTIVE);
        itemsTypeEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        itemsTypeEntity.setUserDelete(Constants.AUDIT_ADMIN);
        return itemsTypeEntity;
    }

    @Override
    public ResponseBase findItemTypeServiceById(Integer id) {
        if (itemsTypeRepository.existsById(id)) {
            Optional<ItemsTypeEntity> itemsTypeEntity = itemsTypeRepository.findById(id);
            if (itemsTypeEntity.isPresent()) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(itemsTypeEntity));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findAllItemsType() {
        List<ItemsTypeEntity> itemsTypeEntityList = itemsTypeRepository.findAll();
        if (!itemsTypeEntityList.isEmpty()) {
            itemsTypeEntityList.sort(Comparator.comparingInt(ItemsTypeEntity::getItemsTypeId));
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(itemsTypeEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    @Override
    public ResponseBase updateItemTypeService(Integer id, RequestItemType requestItemType) {
        if (itemsTypeRepository.existsById(id)) {
            Optional<ItemsTypeEntity> itemsTypeEntity = itemsTypeRepository.findById(id);
            if (itemsTypeEntity.isPresent()) {
                boolean validateInput = itemsTypeValidation.validateInput(requestItemType);
                if (validateInput) {
                    ItemsTypeEntity itemsTypeUpdated = getEntityUpdate(itemsTypeEntity.get(), requestItemType);
                    itemsTypeRepository.save(itemsTypeUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(itemsTypeUpdated));
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

    private ItemsTypeEntity getEntityUpdate(ItemsTypeEntity itemsTypeEntity, RequestItemType requestItemType) {
        itemsTypeEntity.setItemType(requestItemType.getItemType());
        itemsTypeEntity.setName(requestItemType.getName());
        itemsTypeEntity.setImage(requestItemType.getImage());
        itemsTypeEntity.setPrice(requestItemType.getPrice());
        itemsTypeEntity.setSize(requestItemType.getSize());
        itemsTypeEntity.setIngredients(requestItemType.getIngredients());
        itemsTypeEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        itemsTypeEntity.setUserModify(Constants.AUDIT_ADMIN);
        return itemsTypeEntity;
    }
}
