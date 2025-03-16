package com.fawry.coupon_api.mapper;

import com.fawry.coupon_api.dto.CouponDTO;
import com.fawry.coupon_api.entity.Coupon;
import com.fawry.coupon_api.enums.DiscountType;
import com.fawry.coupon_api.repository.CouponRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CouponMapper {
    private final CouponRepository couponRepository;

    public CouponMapper(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon mapToCoupon(CouponDTO couponDTO) {
        return couponRepository.findByCouponCode(couponDTO.getCouponCode())
                .orElseThrow(() -> new EntityNotFoundException("Coupon with code " + couponDTO.getCouponCode() + " not found"));
    }
    public CouponDTO mapToCouponDTO(Coupon coupon) {
        return CouponDTO.builder()
                .couponCode(coupon.getCouponCode())
                .discountType(coupon.getDiscountType().name())
                .discountValue(coupon.getDiscountValue())
                .expiryDate(coupon.getExpiryDate())
                .usageLimit(coupon.getUsageLimit())
                .Id(coupon.getId())
                .isActive(coupon.getIsActive())
                .timesUsed(coupon.getTimesUsed())
                .build();
    }

    public List<CouponDTO> mapToListCouponDTOs(List<Coupon> coupons) {
        return coupons.stream()
                .map(this::mapToCouponDTO)
                .toList();
    }
}
