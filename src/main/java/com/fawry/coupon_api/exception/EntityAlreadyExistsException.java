package com.fawry.coupon_api.exception;

import com.fawry.coupon_api.enums.ErrorCode;
import com.thoughtworks.xstream.core.BaseException;

public class EntityAlreadyExistsException extends BaseException {
    public EntityAlreadyExistsException(String entityName, String identifier) {
        super(String.format("%s already exists with identifier: %s", entityName, identifier));
    }
}