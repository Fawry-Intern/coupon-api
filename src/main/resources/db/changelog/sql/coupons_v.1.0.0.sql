CREATE TABLE coupon_consumption (
    id BIGSERIAL PRIMARY KEY,
    consumption_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    order_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    actual_discount NUMERIC(10,2) NOT NULL CHECK (actual_discount > 0),
    coupon_id BIGINT NOT NULL,
    CONSTRAINT fk_coupon FOREIGN KEY (coupon_id) REFERENCES Coupons (coupon_id) ON DELETE CASCADE
);
