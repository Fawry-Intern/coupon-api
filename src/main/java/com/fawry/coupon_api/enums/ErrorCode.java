package com.fawry.coupon_api.enums;

import lombok.Builder;
import org.springframework.http.HttpStatus;

public enum ErrorCode {

    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "Coupon not found"),
    COUPON_ALREADY_EXISTS(HttpStatus.CONFLICT, "Coupon already exists"),

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation failed"),


    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"),
    OPERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Operation failed");

    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(HttpStatus status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}