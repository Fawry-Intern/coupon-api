package com.fawry.coupon_api.service;

import com.fawry.coupon_api.dto.ConsumeCouponRequestDTO;
import com.fawry.coupon_api.dto.CouponDTO;
import com.fawry.coupon_api.dto.DiscountDTO;

import java.util.List;

public interface CouponService {
    void createCoupon(CouponDTO couponDTO);

    List<CouponDTO> getAllCoupons();

    CouponDTO getCouponByCode(String couponCode);

    void activateCoupon(String couponCode);

    void deactivateCoupon(String couponCode);

    DiscountDTO consumeCoupon(ConsumeCouponRequestDTO consumeCouponRequestDTO);

    void deleteCoupon(String couponCode);
}
