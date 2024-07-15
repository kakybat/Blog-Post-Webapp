package com.kakybat.validations;

import com.kakybat.annotation.PasswordMatch;
import com.kakybat.model.Person ;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object>  {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void initialize(PasswordMatch constraintAnnotation){
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context){
        Person user = (Person) value;
        String rawPassword = user.getPassword();
        String rawConfirmPassword = user.getConfirmPassword();

        boolean valid = rawPassword != null && rawPassword.equals(rawConfirmPassword);
        if(!valid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords do not match")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }
        return valid;
    }
}
