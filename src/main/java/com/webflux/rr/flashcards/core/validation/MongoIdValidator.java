package com.webflux.rr.flashcards.core.validation;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoIdValidator implements ConstraintValidator<MongoId, String> {

    private final Logger log = LoggerFactory.getLogger(MongoIdValidator.class);

    @Override
    public void initialize(MongoId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info("===== checking if {} is a valid mongoDB id", value);
        return StringUtils.isNotBlank(value) && ObjectId.isValid(value);
    }
}