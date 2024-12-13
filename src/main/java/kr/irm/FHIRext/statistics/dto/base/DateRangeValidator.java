package kr.irm.FHIRext.statistics.dto.base;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

    private String startRange;
    private String endRange;
    private String date;

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        this.startRange = constraintAnnotation.startRange();
        this.endRange = constraintAnnotation.endRange();
        this.date = constraintAnnotation.date();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Class<?> clazz = value.getClass();
            Field startRangeField = clazz.getDeclaredField(startRange);
            Field endRangeField = clazz.getDeclaredField(endRange);
            Field dateField = clazz.getDeclaredField(date);

            startRangeField.setAccessible(true);
            endRangeField.setAccessible(true);
            dateField.setAccessible(true);

            LocalDate startValue = (LocalDate) startRangeField.get(value);
            LocalDate endValue = (LocalDate) endRangeField.get(value);
            LocalDate dateValue = (LocalDate) dateField.get(value);

            if (dateValue != null) {
                if (startValue != null || endValue != null) {
                    return false;
                }
            }

            if (startValue != null || endValue != null) {
                if (dateValue != null) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
