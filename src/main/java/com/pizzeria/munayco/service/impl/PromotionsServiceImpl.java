package com.pizzeria.munayco.service.impl;

import com.pizzeria.munayco.aggregates.constants.Constants;
import com.pizzeria.munayco.aggregates.request.RequestPromotion;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.entity.PromotionsEntity;
import com.pizzeria.munayco.repository.PromotionsRepository;
import com.pizzeria.munayco.service.PromotionsService;
import com.pizzeria.munayco.util.PromotionValidations;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionsServiceImpl implements PromotionsService {
    private final PromotionsRepository promotionsRepository;
    private final PromotionValidations promotionValidations;

    public PromotionsServiceImpl(PromotionsRepository promotionsRepository, PromotionValidations promotionValidations) {
        this.promotionsRepository = promotionsRepository;
        this.promotionValidations = promotionValidations;
    }

    @Override
    public ResponseBase createPromotion(RequestPromotion requestPromotion) {
        boolean validateInput = promotionValidations.validateInput(requestPromotion);
        if (validateInput) {
            PromotionsEntity promotionsEntity = getEntityCreate(requestPromotion);
            promotionsRepository.save(promotionsEntity);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(promotionsEntity));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    private PromotionsEntity getEntityCreate(RequestPromotion requestPromotion) {
        PromotionsEntity promotionsEntity = new PromotionsEntity();
        PromotionsEntity lastPromotion = promotionsRepository.findFirstByOrderByPromotionIdDesc();
        if (lastPromotion == null) {
            promotionsEntity.setPromoCode("PPC1");
        } else {
            promotionsEntity.setPromoCode("PPC" + (lastPromotion.getPromotionId()+1));
        }
        promotionsEntity.setName(requestPromotion.getName());
        promotionsEntity.setValue(requestPromotion.getValue());
        promotionsEntity.setDescription(requestPromotion.getDescription());
        promotionsEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        promotionsEntity.setUserCreate(Constants.AUDIT_ADMIN);
        promotionsEntity.setStatus(Constants.STATUS_ACTIVE);
        return promotionsEntity;
    }

    @Override
    public ResponseBase deletePromotionById(Integer id) {
        if (promotionsRepository.existsById(id)) {
            Optional<PromotionsEntity> promotionsEntity = promotionsRepository.findById(id);
            if (promotionsEntity.isPresent()) {
                PromotionsEntity promotionsEntityDeleted = getEntityDelete(promotionsEntity.get());
                promotionsRepository.save(promotionsEntityDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(promotionsEntityDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }

        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    private PromotionsEntity getEntityDelete(PromotionsEntity promotionsEntity) {
        promotionsEntity.setStatus(Constants.STATUS_INACTIVE);
        promotionsEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        promotionsEntity.setUserDelete(Constants.AUDIT_ADMIN);
        return promotionsEntity;
    }

    @Override
    public ResponseBase findAllPromotions() {
        List<PromotionsEntity> promotionsEntityList = promotionsRepository.findAll();
        if (!promotionsEntityList.isEmpty()) {
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(promotionsEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    @Override
    public ResponseBase findPromotionById(Integer id) {
        if (promotionsRepository.existsById(id)) {
            Optional<PromotionsEntity> promotionsEntity = promotionsRepository.findById(id);
            if (promotionsEntity.isPresent()) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(promotionsEntity));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase updatePromotionById(Integer id, RequestPromotion requestPromotion) {
        if (promotionsRepository.existsById(id)) {
            Optional<PromotionsEntity> promotionsEntity = promotionsRepository.findById(id);
            if (promotionsEntity.isPresent()) {
                boolean validateInput = promotionValidations.validateInput(requestPromotion);
                if (validateInput) {
                    PromotionsEntity promotionUpdated = getEntityUpdate(promotionsEntity.get(), requestPromotion);
                    promotionsRepository.save(promotionUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(promotionUpdated));
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

    private PromotionsEntity getEntityUpdate(PromotionsEntity promotionsEntity, RequestPromotion requestPromotion) {
        promotionsEntity.setName(requestPromotion.getName());
        promotionsEntity.setDescription(requestPromotion.getDescription());
        promotionsEntity.setValue(requestPromotion.getValue());
        promotionsEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        promotionsEntity.setUserModify(Constants.AUDIT_ADMIN);
        return promotionsEntity;
    }
}
