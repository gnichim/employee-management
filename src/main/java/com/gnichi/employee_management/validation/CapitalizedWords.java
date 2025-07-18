package com.gnichi.employee_management.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// This tells the validation framework: “When you see @CapitalizedWords, use CapitalizedWordsValidator to validate it.”
@Constraint(validatedBy = CapitalizedWordsValidator.class)
// This defines where the annotation can be used.
// ElementType.FIELD means you can only use it on fields (like jobTitle).
@Target({ ElementType.FIELD })
// This means the annotation is available at runtime, so the validation framework can read it during execution.
// Without this, the annotation would be ignored at runtime.
@Retention(RetentionPolicy.RUNTIME)
public @interface CapitalizedWords {
    String message() default "Each word in job title must start with a capital letter";
    Class<?>[] groups() default {}; // groups: Used for grouping validations
    Class<? extends Payload>[] payload() default {};
}
