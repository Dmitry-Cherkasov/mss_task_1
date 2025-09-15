package org.mss.song.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CsvLengthValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CsvMaxLength {
    String message() default "CSV string is too long: received {length}, maximum allowed is {max}";

    int max();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}