package org.mss.song.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CsvLengthValidator implements ConstraintValidator<CsvMaxLength, String> {

    private int max;

    @Override
    public void initialize(CsvMaxLength annotation) {
        this.max = annotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        int length = value.length();
        if (length <= max) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                "CSV string is too long: received " + length + ", maximum allowed is " + max
        ).addConstraintViolation();

        return false;
    }
}
