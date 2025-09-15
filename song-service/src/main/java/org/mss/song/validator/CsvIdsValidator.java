package org.mss.song.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CsvIdsValidator implements ConstraintValidator<ValidCsvIds, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;

        String[] parts = value.split(",");
        for (String part : parts) {
            try {
                int id = Integer.parseInt(part);
                if (id <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        String.format("Invalid ID format: '%s'. Only positive integers are allowed", part)
                ).addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
