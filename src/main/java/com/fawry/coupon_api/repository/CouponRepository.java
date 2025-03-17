package com.fawry.coupon_api.repository;

import com.fawry.coupon_api.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    boolean existsByCouponCode(String couponCode);

    Optional<Coupon> findByCouponCode(String couponCode);
}
