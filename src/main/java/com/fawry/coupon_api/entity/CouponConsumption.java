package com.fawry.coupon_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Instant consumptionDate;

    @NotNull(message = "Order id is mandatory")
    private Long orderId;

    @NotNull(message = "customer id is mandatory")
    private Long customerId;

    @NotNull(message = "Actual discount is mandatory")
    @Positive(message = "Actual discount should be positive")
    private BigDecimal actualDiscount;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

}
