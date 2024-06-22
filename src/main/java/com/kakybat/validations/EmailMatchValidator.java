//package com.kakybat.validations;
//
//import com.kakybat.annotation.EmailMatch;
//import com.kakybat.model.User;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//public class EmailMatchValidator implements ConstraintValidator<EmailMatch, Object> {
//    @Override
//    public void initialize(EmailMatch constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }
//
//    @Override
//    public boolean isValid(Object value, ConstraintValidatorContext context) {
//        User user = (User) value;
//        return user.getEmail().equals(user.getConfirmEmail());
//    }
//}
