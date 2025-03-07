package com.fawry.coupon_api.entity;

import com.fawry.coupon_api.enums.DiscountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id", nullable = false)
    private Long id;

    @NonNull
    @Min(value = 0, message = "Usage limit must be a positive number")
    @Column(name = "usage_limit", nullable = false)
    private Integer usageLimit;

    @NonNull
    @Min(value = 0, message = "Times used must be a positive number")
    @Column(name = "times_used", nullable = false)
    private Integer timesUsed = 0;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @NotBlank(message = "Coupon code must not be blank")
    @Column(name = "coupon_code", nullable = false, unique = true, length = 20)
    private String couponCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;


    @PrePersist
    @PreUpdate
    private void validateUsage() {
        if (timesUsed > usageLimit) {
            throw new IllegalArgumentException("Times used cannot exceed usage limit");
        }
    }
}
