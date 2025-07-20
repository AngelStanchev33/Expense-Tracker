package com.angelstanchev.expense_tracker.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;


    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            Class<?> clazz = value.getClass();

            Field firstField = clazz.getDeclaredField(firstFieldName);
            firstField.setAccessible(true);

            Field secondField = clazz.getDeclaredField(secondFieldName);
            secondField.setAccessible(true);

            Object firstValue = firstField.get(value);
            Object secondValue = secondField.get(value);

            return Objects.equals(firstValue, secondValue);

        } catch (Exception e) {

            return false;
        }
    }
}
