package com.fawry.coupon_api.controller;

import com.fawry.coupon_api.dto.CouponConsumptionDTO;
import com.fawry.coupon_api.service.ConsumptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/consumptions")
@AllArgsConstructor
public class ConsumptionController {
    private final ConsumptionService consumptionService;
    @GetMapping
    public ResponseEntity<List<CouponConsumptionDTO>> getAllConsumptions() {
        return ResponseEntity.ok(consumptionService.getAllConsumptions());
    }

    @GetMapping("{couponCode}")
    public ResponseEntity<List<CouponConsumptionDTO>> getCouponConsumptionByCode(@PathVariable String couponCode) {
        return ResponseEntity.ok(consumptionService.getCouponConsumptionByCode(couponCode));
    }
}
