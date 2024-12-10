package com.orrot.store.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, String> {

    private Set<String> values;

    @Override
    public void initialize(EnumValidator annotation) {
        this.values = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .map(String::toUpperCase)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(Objects.isNull(value)) {
            return true;
        }
        modifyErrorMessage(value, constraintValidatorContext);
        return  values.contains(value.toUpperCase());
    }

    private void modifyErrorMessage(String value, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        String message = Optional.ofNullable(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                .map(defaultMessage -> defaultMessage.replace("{value}", value))
                .map(defaultMessage -> defaultMessage.replace("{possibleValues}", values.toString()))
                .orElse(StringUtils.EMPTY);
        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
