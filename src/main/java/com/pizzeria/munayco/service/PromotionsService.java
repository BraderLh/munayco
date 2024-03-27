package com.pizzeria.munayco.service;

import com.pizzeria.munayco.aggregates.request.RequestPromotion;
import com.pizzeria.munayco.aggregates.response.ResponseBase;

public interface PromotionsService{
    ResponseBase createPromotion(RequestPromotion requestPromotion);
    ResponseBase deletePromotionById(Integer id);
    ResponseBase findAllPromotions();
    ResponseBase findPromotionById(Integer id);
    ResponseBase updatePromotionById(Integer id, RequestPromotion requestPromotion);
}
