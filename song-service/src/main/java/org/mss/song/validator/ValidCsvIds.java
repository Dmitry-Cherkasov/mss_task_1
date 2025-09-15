package org.mss.song.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CsvIdsValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCsvIds {
    String message() default "Invalid ID format: '{invalidValue}'. Only positive integers are allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
