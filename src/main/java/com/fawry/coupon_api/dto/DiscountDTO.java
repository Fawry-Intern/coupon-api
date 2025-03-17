package com.fawry.coupon_api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DiscountDTO {
    private BigDecimal actualDiscount;
}
