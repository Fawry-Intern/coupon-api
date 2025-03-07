CREATE TABLE Coupons (
    coupon_id      BIGSERIAL PRIMARY KEY,
    usage_limit    INT NOT NULL CHECK (usage_limit >= 0),
    times_used     INT NOT NULL DEFAULT 0 CHECK (times_used >= 0),
    expiry_date    TIMESTAMP NOT NULL,
    is_active      BOOLEAN NOT NULL DEFAULT TRUE,
    coupon_code    VARCHAR(20) UNIQUE NOT NULL,
    discount_type  VARCHAR(20) NOT NULL CHECK (discount_type IN ('percentage', 'fixed_amount')),
    discount_value NUMERIC(10,2) NOT NULL CHECK (discount_value >= 0)
    CHECK (times_used <= usage_limit)
);