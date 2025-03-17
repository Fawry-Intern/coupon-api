package com.fawry.coupon_api.service;

import com.fawry.coupon_api.dto.CouponConsumptionDTO;
import com.fawry.coupon_api.mapper.CouponConsumptionMapper;
import com.fawry.coupon_api.repository.CouponConsumptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {
    private final CouponConsumptionRepository couponConsumptionRepository;
    private final CouponConsumptionMapper couponConsumptionMapper;

    public ConsumptionServiceImpl(CouponConsumptionRepository couponConsumptionRepository, CouponConsumptionMapper couponConsumptionMapper) {
        this.couponConsumptionRepository = couponConsumptionRepository;
        this.couponConsumptionMapper = couponConsumptionMapper;
    }

    public List<CouponConsumptionDTO> getAllConsumptions() {
        return couponConsumptionMapper.mapToDTO(couponConsumptionRepository.findAll());
    }

    @Override
    public List<CouponConsumptionDTO> getCouponConsumptionByCode(String couponCode) {
        return couponConsumptionMapper.mapToDTO(couponConsumptionRepository.findByCoupon_CouponCode(couponCode));
    }

}
