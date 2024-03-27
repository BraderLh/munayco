package com.pizzeria.munayco.service.impl;

import com.pizzeria.munayco.aggregates.constants.Constants;
import com.pizzeria.munayco.aggregates.request.RequestItem;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.entity.ItemsEntity;
import com.pizzeria.munayco.entity.ItemsTypeEntity;
import com.pizzeria.munayco.entity.PromotionsEntity;
import com.pizzeria.munayco.repository.ItemsRepository;
import com.pizzeria.munayco.repository.ItemsTypeRepository;
import com.pizzeria.munayco.repository.PromotionsRepository;
import com.pizzeria.munayco.service.ItemsService;
import com.pizzeria.munayco.util.ItemValidations;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemsServiceImpl implements ItemsService {
    private final ItemsRepository itemsRepository;
    private final ItemValidations itemValidations;
    private final ItemsTypeRepository itemsTypeRepository;
    private final PromotionsRepository promotionsRepository;

    public ItemsServiceImpl(ItemsRepository itemsRepository, ItemValidations itemValidations, ItemsTypeRepository itemsTypeRepository, PromotionsRepository promotionsRepository) {
        this.itemsRepository = itemsRepository;
        this.itemValidations = itemValidations;
        this.itemsTypeRepository = itemsTypeRepository;
        this.promotionsRepository = promotionsRepository;
    }

    @Override
    public ResponseBase createItem(RequestItem requestItem) {
        boolean validateInput = itemValidations.validateInput(requestItem);
        if (validateInput) {
            ItemsEntity itemsEntity = getEntityCreate(requestItem);
            itemsRepository.save(itemsEntity);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(itemsEntity));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    private ItemsEntity getEntityCreate(RequestItem requestItem) {
        ItemsEntity itemsEntity = new ItemsEntity();
        itemsEntity.setItemsTypeEntity(getItemTypeEntity(requestItem.getItemsTypeEntityId()));
        itemsEntity.setPromotionsEntitySet(requestItem.getPromotionsEntitySet());
        itemsEntity.setCost(applyPromotion(requestItem));
        itemsEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        itemsEntity.setUserCreate(Constants.AUDIT_ADMIN);
        itemsEntity.setStatus(Constants.STATUS_ACTIVE);
        return itemsEntity;
    }

    private Double applyPromotion(RequestItem requestItem) {
        ItemsTypeEntity itemsTypePromotion = getItemTypeEntity(requestItem.getItemsTypeEntityId());
        Set<PromotionsEntity>promotionsEntitySet = requestItem.getPromotionsEntitySet();
        double finalPrice;
        if (itemsTypePromotion != null) {
            if (!promotionsEntitySet.isEmpty()) {
                finalPrice = calculatePromo(promotionsEntitySet, itemsTypePromotion.getPrice());
            } else {
                finalPrice = itemsTypePromotion.getPrice();
            }
        } else {
            finalPrice = 0.0;
        }
        return finalPrice;
    }

    private Double calculatePromo(Set<PromotionsEntity> promotionsEntitySet, Double price) {
        double totalDiscount = 0.0;
        for (PromotionsEntity promotionsEntity : promotionsEntitySet) {
            Optional<PromotionsEntity> promotion = promotionsRepository.findById(promotionsEntity.getPromotionId());
            if (promotion.isPresent()) {
                totalDiscount = totalDiscount + (price * ((double) promotion.get().getValue() / 100));
            } else {
                totalDiscount = 0.0;
            }
        }
        return price - totalDiscount;
    }

    private ItemsTypeEntity getItemTypeEntity(int itemsTypeEntityId) {
        Optional<ItemsTypeEntity> itemsTypeEntity = itemsTypeRepository.findById(itemsTypeEntityId);
        return itemsTypeEntity.orElse(null);
    }

    @Override
    public ResponseBase deleteItemById(Integer id) {
        if (itemsRepository.existsById(id)) {
            Optional<ItemsEntity> itemsEntity = itemsRepository.findById(id);
            if (itemsEntity.isPresent()) {
                ItemsEntity itemsDeleted = getEntityDelete(itemsEntity.get());
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(itemsDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }

        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    private ItemsEntity getEntityDelete(ItemsEntity itemsEntity) {
        itemsEntity.setStatus(Constants.STATUS_INACTIVE);
        itemsEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        itemsEntity.setUserDelete(Constants.AUDIT_ADMIN);
        return itemsEntity;
    }

    @Override
    public ResponseBase findItemById(Integer id) {
        if (itemsRepository.existsById(id)) {
            Optional<ItemsEntity> itemsEntity = itemsRepository.findById(id);
            if (itemsEntity.isPresent()) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(itemsEntity));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findAllItems() {
        List<ItemsEntity> itemsEntityList = itemsRepository.findAll();
        if (!itemsEntityList.isEmpty()) {
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(itemsEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    @Override
    public ResponseBase updateItemById(Integer id, RequestItem requestItem) {
        if (itemsRepository.existsById(id)) {
            Optional<ItemsEntity> itemsEntity = itemsRepository.findById(id);
            if (itemsEntity.isPresent()) {
                boolean validateInput = itemValidations.validateInput(requestItem);
                if (validateInput) {
                    ItemsEntity itemsUpdated = getEntityUpdate(itemsEntity.get(), requestItem);
                    itemsRepository.save(itemsUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(itemsUpdated));
                } else {
                    return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
                }
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_UPDATE, Optional.empty());
            }

        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    private ItemsEntity getEntityUpdate(ItemsEntity itemsEntity, RequestItem requestItem) {
        itemsEntity.setItemsTypeEntity(getItemTypeEntity(requestItem.getItemsTypeEntityId()));
        itemsEntity.setPromotionsEntitySet(requestItem.getPromotionsEntitySet());
        itemsEntity.setCost(applyPromotion(requestItem));
        itemsEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        itemsEntity.setUserModify(Constants.AUDIT_ADMIN);
        return itemsEntity;
    }
}
