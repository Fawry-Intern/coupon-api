ALTER TABLE Coupons
DROP CONSTRAINT Coupons_discount_type_check,
ADD CONSTRAINT Coupons_discount_type_check
CHECK (discount_type IN ('PERCENTAGE', 'FIXED_AMOUNT'));
