package com.kakybat.annotation;

import com.kakybat.validations.EmailMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailMatchValidator.class)
public @interface EmailMatch {
    String message() default "Emails do not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
