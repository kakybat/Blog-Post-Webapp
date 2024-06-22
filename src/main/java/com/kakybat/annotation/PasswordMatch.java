//package com.kakybat.annotation;
//
//import com.kakybat.validations.PasswordMatchValidator;
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.*;
//
//@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = PasswordMatchValidator.class)
//@Documented
//public @interface PasswordMatch {
//    String message() default "Password do not match";
//    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default {};
//}
