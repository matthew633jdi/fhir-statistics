package kr.irm.FHIRext.statistics.dto.base;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface ValidDateRange {
    String message();
    String startRange();
    String endRange();
    String date();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
