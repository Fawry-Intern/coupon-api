package com.fawry.coupon_api.repository;

import com.fawry.coupon_api.entity.CouponConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponConsumptionRepository extends JpaRepository<CouponConsumption, Long> {
    List<CouponConsumption> findByCoupon_CouponCode(String couponCode);
}
