package kr.irm.FHIRext.statistics.dto.base;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class DateBeforeValidator implements ConstraintValidator<DateBefore, Object> {

    private String startDate;
    private String endDate;

    @Override
    public void initialize(DateBefore constraintAnnotation) {
        this.startDate = constraintAnnotation.start();
        this.endDate = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Class<?> clazz = value.getClass();
            Field startField = clazz.getDeclaredField(startDate);
            Field endField = clazz.getDeclaredField(endDate);

            startField.setAccessible(true);
            endField.setAccessible(true);

            LocalDate startValue = (LocalDate) startField.get(value);
            LocalDate endValue = (LocalDate) endField.get(value);

            if (startValue == null || endValue == null) {
                return true;
            }

            return startValue.isBefore(endValue);
        } catch (Exception e) {
            return false;
        }
    }
}
