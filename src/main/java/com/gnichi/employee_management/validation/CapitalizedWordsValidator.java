package com.gnichi.employee_management.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// This tells the framework: “I’m validating @CapitalizedWords applied to a String field.”
public class CapitalizedWordsValidator implements ConstraintValidator<CapitalizedWords, String> {

    // @Override is a Java annotation that tells the compiler:
    // “This method is intended to override a method from a superclass or interface.”
    // It’s not required by the compiler, but it’s highly recommended because it helps catch mistakes early.
    // example: typo "isvalid", here without @Override it would silently compile and never be called.
    @Override
    // This method is called automatically during validation.
    // It returns true if the value is valid, false if it’s not.
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // We allow null or blank values here.
        // Why? Because we assume other annotations like @NotBlank will handle that.
        // This validator only cares about capitalization.
        if (value == null || value.isBlank()) return true;

        // Split the string into words.
        // For each word, check if the first character is uppercase.
        // If any word fails, return false.
        for (String word : value.split(" ")) {
            if (word.isEmpty()) continue;
            if (!Character.isUpperCase(word.charAt(0))) {
                return false;
            }
        }
        // If all words pass, the value is valid.
        return true;
    }
}
