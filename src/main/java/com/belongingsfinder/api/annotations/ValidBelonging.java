package com.belongingsfinder.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.belongingsfinder.api.model.validator.BelongingValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BelongingValidator.class)
@Documented
public @interface ValidBelonging {

	Class<?>[] groups() default {};

	String message() default "{com.belongingsfinder.api.model.validator}";

	Class<? extends Payload>[] payload() default {};

}
