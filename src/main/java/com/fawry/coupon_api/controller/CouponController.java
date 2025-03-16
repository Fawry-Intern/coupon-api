package com.fawry.coupon_api.controller;

import com.fawry.coupon_api.dto.ConsumeCouponRequestDTO;
import com.fawry.coupon_api.dto.CouponDTO;
import com.fawry.coupon_api.dto.DiscountDTO;
import com.fawry.coupon_api.service.CouponService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@AllArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/create")
    public ResponseEntity<Void> createCoupon(@Valid @RequestBody CouponDTO couponDto) {
        couponService.createCoupon(couponDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<CouponDTO>> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }

    @GetMapping("/{couponCode}")
    public ResponseEntity<CouponDTO> getCouponByCode(@PathVariable String couponCode) {
        return ResponseEntity.ok(couponService.getCouponByCode(couponCode));
    }

    @PutMapping("/{couponCode}/activate")
    public ResponseEntity<Void> activateCoupon(@PathVariable String couponCode) {
        couponService.activateCoupon(couponCode);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{couponCode}/deactivate")
    public ResponseEntity<Void> deactivateCoupon(@PathVariable String couponCode) {
        couponService.deactivateCoupon(couponCode);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/consume")
    public ResponseEntity<DiscountDTO> consumeCoupon(@Valid @RequestBody ConsumeCouponRequestDTO consumeCouponRequestDTO) {
        return ResponseEntity.ok(couponService.consumeCoupon(consumeCouponRequestDTO));
    }

}
