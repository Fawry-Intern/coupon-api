package com.fawry.coupon_api.dto;

import com.fawry.coupon_api.entity.Coupon;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class CouponConsumptionDTO {
    private Long id;

    private Instant consumptionDate;

    @NotNull(message = "Order id is mandatory")
    private Long orderId;

    @NotNull(message = "customer id is mandatory")
    private Long customerId;

    @NotNull(message = "Actual discount is mandatory")
    @Positive(message = "Actual discount should be positive")
    private BigDecimal actualDiscount;

    Coupon coupon;
}
