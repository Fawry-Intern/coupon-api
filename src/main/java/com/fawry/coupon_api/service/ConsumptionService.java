package com.fawry.coupon_api.service;

import com.fawry.coupon_api.dto.CouponConsumptionDTO;

import java.util.List;

public interface ConsumptionService {
    List<CouponConsumptionDTO> getAllConsumptions();

    List<CouponConsumptionDTO> getCouponConsumptionByCode(String couponCode);
}
