package com.fawry.coupon_api.service;

import com.fawry.coupon_api.dto.ConsumeCouponRequestDTO;
import com.fawry.coupon_api.dto.CouponDTO;
import com.fawry.coupon_api.dto.DiscountDTO;
import com.fawry.coupon_api.entity.Coupon;
import com.fawry.coupon_api.entity.CouponConsumption;
import com.fawry.coupon_api.enums.DiscountType;
import com.fawry.coupon_api.exception.EntityAlreadyExistsException;
import com.fawry.coupon_api.mapper.CouponMapper;
import com.fawry.coupon_api.repository.CouponConsumptionRepository;
import com.fawry.coupon_api.repository.CouponRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;
    private final HttpServletRequest httpServletRequest;
    private final CouponConsumptionRepository couponConsumptionRepository;
    @Transactional
    public void createCoupon(CouponDTO couponDTO) {
        if (Boolean.TRUE.equals(couponRepository.existsByCouponCode(couponDTO.getCouponCode()))) {
            throw new EntityAlreadyExistsException("Coupon", couponDTO.getCouponCode());
        }
        Coupon coupon = Coupon.builder()
                .couponCode(couponDTO.getCouponCode())
                .discountValue(couponDTO.getDiscountValue())
                .expiryDate(couponDTO.getExpiryDate())
                .discountType(DiscountType.valueOf(couponDTO.getDiscountType()))
                .usageLimit(couponDTO.getUsageLimit())
                .timesUsed(0)
                .isActive(true)
                .build();
        couponRepository.save(coupon);
    }

    @Override
    public List<CouponDTO> getAllCoupons() {
        return couponMapper.mapToListCouponDTOs(couponRepository.findAll());
    }

    @Override
    public CouponDTO getCouponByCode(String couponCode) {
        return couponMapper.mapToCouponDTO(couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new EntityNotFoundException("Coupon with code " + couponCode + " not found")));
    }

    @Override
    public void activateCoupon(String couponCode) {
        Coupon coupon = couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new EntityNotFoundException("Coupon with code " + couponCode + " not found"));
        coupon.setIsActive(true);
        couponRepository.save(coupon);
    }

    @Override
    public void deactivateCoupon(String couponCode) {
        Coupon coupon = couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new EntityNotFoundException("Coupon with code " + couponCode + " not found"));
        coupon.setIsActive(false);
        couponRepository.save(coupon);
    }
    private String couponDiscountType(Coupon coupon) {
        return coupon.getDiscountType().name();
    }
    private boolean isCouponValid(Coupon coupon) {
        return coupon.getIsActive() && coupon.getExpiryDate().isAfter(Instant.now()) && coupon.getTimesUsed() < coupon.getUsageLimit();
    }
    private DiscountDTO calculatePercentageDiscount(Coupon coupon, BigDecimal amount) {
        BigDecimal discountAmount = amount
                .multiply(coupon.getDiscountValue().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));

        return DiscountDTO.builder()
                .actualDiscount(discountAmount)
                .build();
    }

    private DiscountDTO calculateFixedDiscount(Coupon coupon, BigDecimal amount) {
        return DiscountDTO.builder()
                .actualDiscount(coupon.getDiscountValue().min(amount))
                .build();
    }

    @Transactional
    @Override
    public DiscountDTO consumeCoupon(ConsumeCouponRequestDTO consumeCouponRequestDTO) {
        Coupon coupon = couponRepository.findByCouponCode(consumeCouponRequestDTO.getCouponCode())
                .orElseThrow(() -> new EntityNotFoundException("Coupon with code " + consumeCouponRequestDTO.getCouponCode() + " not found"));

        if (!isCouponValid(coupon)) {
            throw new IllegalArgumentException("Coupon is not valid");
        }

        DiscountDTO discountDTO;
        if (couponDiscountType(coupon).equals(DiscountType.PERCENTAGE.name())) {
            discountDTO = calculatePercentageDiscount(coupon, consumeCouponRequestDTO.getOrderAmount());
        } else {
            discountDTO = calculateFixedDiscount(coupon, consumeCouponRequestDTO.getOrderAmount());
        }

        coupon.setTimesUsed(coupon.getTimesUsed() + 1);
        couponRepository.save(coupon);
        saveCouponConsumption(consumeCouponRequestDTO, discountDTO.getActualDiscount(),coupon);
        return discountDTO;
    }

    private void saveCouponConsumption(ConsumeCouponRequestDTO consumeCouponRequestDTO, BigDecimal actualDiscount,Coupon coupon) {
        CouponConsumption couponConsumption= CouponConsumption.builder()
                .orderId(consumeCouponRequestDTO.getOrderId())
                .customerId(getUserId())
                .actualDiscount(actualDiscount)
                .consumptionDate(Instant.now())
                .coupon(coupon)
                .build();

        couponConsumptionRepository.save(couponConsumption);
    }

    private Long getUserId () {
        String id = httpServletRequest.getHeader("UserId");

        if (id == null) {
            throw new IllegalArgumentException("UserId header is missing");
        }

        long userId;
        try {
            userId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid UserId format");
        }
        return userId;
    }
}
