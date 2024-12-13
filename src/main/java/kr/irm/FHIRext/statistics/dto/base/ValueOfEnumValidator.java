package kr.irm.FHIRext.statistics.dto.base;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, String> {

    private String[] acceptedValues;

    @Override
    public void initialize(ValueOfEnum constraintAnnotation) {
        acceptedValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(e -> e.name().toLowerCase())
                .toArray(String[]::new);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return Arrays.asList(acceptedValues).contains(value);
    }
}
