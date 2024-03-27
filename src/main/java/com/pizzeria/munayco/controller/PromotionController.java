package com.pizzeria.munayco.controller;

import com.pizzeria.munayco.aggregates.request.RequestPromotion;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.service.PromotionsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/promotion")
public class PromotionController {
    public final PromotionsService promotionsService;

    public PromotionController(PromotionsService promotionsService) {
        this.promotionsService = promotionsService;
    }

    @PostMapping
    public ResponseBase createPromotion(@RequestBody RequestPromotion requestPromotion) {
        return promotionsService.createPromotion(requestPromotion);
    }

    @DeleteMapping("{id}")
    public ResponseBase deletePromotion(@PathVariable int id) {
        return promotionsService.deletePromotionById(id);
    }

    @GetMapping()
    public ResponseBase findAllPromotions() {
        return promotionsService.findAllPromotions();
    }

    @GetMapping("{id}")
    public ResponseBase findOneItemType(@PathVariable int id) {
        return promotionsService.findPromotionById(id);
    }

    @PatchMapping("{id}")
    public ResponseBase updateItemType(@PathVariable int id, @RequestBody RequestPromotion requestPromotion) {
        return promotionsService.updatePromotionById(id, requestPromotion);
    }
}
