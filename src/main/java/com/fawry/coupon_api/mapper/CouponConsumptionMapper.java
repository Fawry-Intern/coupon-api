package com.fawry.coupon_api.mapper;

import com.fawry.coupon_api.dto.CouponConsumptionDTO;
import com.fawry.coupon_api.entity.CouponConsumption;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CouponConsumptionMapper {
    public CouponConsumptionDTO mapToDTO(CouponConsumption couponConsumption) {
        return CouponConsumptionDTO.builder()
                .consumptionDate(couponConsumption.getConsumptionDate())
                .customerId(couponConsumption.getCustomerId())
                .orderId(couponConsumption.getOrderId())
                .coupon(couponConsumption.getCoupon())
                .actualDiscount(couponConsumption.getActualDiscount())
                .build();
    }

    public List<CouponConsumptionDTO> mapToDTO(List<CouponConsumption> couponConsumptions) {
        return couponConsumptions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
