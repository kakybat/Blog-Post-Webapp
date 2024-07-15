package com.kakybat.validations;

import com.kakybat.annotation.EmailMatch;
import com.kakybat.model.Person;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailMatchValidator implements ConstraintValidator<EmailMatch, Object> {
    @Override
    public void initialize(EmailMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Person person = (Person) value;

        boolean valid = person.getEmail().equals(person.getConfirmEmail());
        if(!valid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Emails do not match")
                    .addPropertyNode("confirmEmail")
                    .addConstraintViolation();
        }
        return valid;
    }
}
