package kr.irm.FHIRext.statistics.dto.base;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValueOfEnumValidator.class)
public @interface ValueOfEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "Invalid value. This field accepts only {enumClass} values.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
