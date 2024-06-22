//package com.kakybat.validations;
//
//import com.kakybat.annotation.PasswordMatch;
//import com.kakybat.model.User;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object>  {
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Override
//    public void initialize(PasswordMatch constraintAnnotation){
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }
//
//    @Override
//    public boolean isValid(Object value, ConstraintValidatorContext context){
//        User user = (User) value;
//        String rawPassword = user.getPassword();
//        String rawConfirmPassword = user.getConfirmPassword();
//
//        String hashedPassword = passwordEncoder.encode(rawPassword);
//        String hashedConfirmPassword = passwordEncoder.encode(rawConfirmPassword);
//        return hashedPassword.equals(hashedConfirmPassword);
//    }
//}
