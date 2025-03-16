package com.fawry.coupon_api.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class CouponDTO {
    private Long Id;
    @NotNull
    @Min(value = 0, message = "Usage limit must be a positive number")
    private Integer usageLimit;

    private Instant expiryDate;

    @NotBlank(message = "Coupon code must not be blank")
    private String couponCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    private String discountType;

    @NotNull
    @Positive(message = "Value should be positive")
    private BigDecimal discountValue;

    private Boolean isActive;

    private Integer timesUsed;
}
