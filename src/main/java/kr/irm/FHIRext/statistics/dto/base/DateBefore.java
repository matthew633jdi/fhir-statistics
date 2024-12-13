package kr.irm.FHIRext.statistics.dto.base;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateBeforeValidator.class)
public @interface DateBefore {
    String message() default "start should be before end";
    String start();
    String end();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
