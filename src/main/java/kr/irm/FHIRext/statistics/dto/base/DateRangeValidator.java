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

            return dateValue == null || !hasDateRange(startValue, endValue);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean hasDateRange(LocalDate startDate, LocalDate endDate) {
        return startDate != null || endDate != null;
    }
}
