package com.fawry.coupon_api.repository;

import com.fawry.coupon_api.entity.CouponConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponConsumptionRepository extends JpaRepository<CouponConsumption, Long> {
    List<CouponConsumption> findByCoupon_CouponCode(String couponCode);

    Optional<CouponConsumption> findByCoupon_CouponCodeAndCustomerId(String couponCode, Long userId);
}
