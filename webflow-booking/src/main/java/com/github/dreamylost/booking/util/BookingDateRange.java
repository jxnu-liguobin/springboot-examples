package com.github.dreamylost.booking.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解
 */
@Constraint(validatedBy = BookingDateRangeValidator.class)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BookingDateRange {

    String message() default "Invalid check-in and check-out date range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
